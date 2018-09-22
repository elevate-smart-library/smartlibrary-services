package ca.smartlibrary.security;

import ca.smartlibrary.exception.UnauthorizedException;
import ca.smartlibrary.configuration.TokenConfiguration;
import ca.smartlibrary.dto.User;
import ca.smartlibrary.service.TokenService;
import ca.smartlibrary.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    private TokenConfiguration tokenConfiguration;
    private TokenService tokenService;
    private UserService userService;

    public AuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.tokenConfiguration = tokenService.getTokenConfiguration();
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {

            // Decode token
            String token = request.getHeader(tokenConfiguration.getHeaderName());
            if (isBlank(token)) {
                throw new UnauthorizedException(request.getPathInfo());
            }

            // Check
            Claims claims = tokenService.parse(token);
            if (claims.getExpiration().before(Date.from(Instant.now()))) {
                throw new Exception("Token has expired");
            }

            // Set Security context
            List<ScopeAuthority> authorities = new ObjectMapper().convertValue(claims.get(TokenAuthentication.AUTHORITIES_CLAIM_KEY), new TypeReference<List<ScopeAuthority>>() {
            });
            TokenAuthentication authentication = new TokenAuthentication(claims, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authorities.contains(ScopeAuthority.PROFILE)) {
                User user = userService.findOne(claims.getSubject());
                authentication.updateWith(user);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

        } finally {
            // Ensure Api is Stateless
            SecurityContextHolder.clearContext();
        }
    }
}
