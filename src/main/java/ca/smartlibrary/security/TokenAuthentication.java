package ca.smartlibrary.security;

import ca.smartlibrary.dto.User;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class TokenAuthentication implements Authentication {
    public static final String AUTHORITIES_CLAIM_KEY = "scopes";

    private final String principal; // User's id
    private final Claims credentials;
    private final List<ScopeAuthority> authorities;
    private boolean authenticated;
    private User details;

    public TokenAuthentication(@Nonnull Claims claims, List<ScopeAuthority> authorities) {
        this(claims, authorities, null);
    }

    public TokenAuthentication(@Nonnull Claims claims, List<ScopeAuthority> authorities, User user) {
        this.authorities = authorities;
        this.principal = claims.getSubject();
        this.credentials = claims;
        this.details = user;
        this.credentials.put(AUTHORITIES_CLAIM_KEY, authorities);
        setAuthenticated(true);
    }

    public User getUser() {
        return details;
    }

    public void updateWith(User user) {
        setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(this);
    }

    public void updateWith(Map<String, ?> credentials) {
        credentials.forEach(this.credentials::put);
        SecurityContextHolder.getContext().setAuthentication(this);
    }

    @Override
    public String getName() {
        return this.principal + "|" + UUID.randomUUID().toString();
    }
}
