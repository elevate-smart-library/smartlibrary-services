package ca.smartlibrary.service;

import ca.smartlibrary.configuration.TokenConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class TokenService {
    @Getter
    private final TokenConfiguration tokenConfiguration;

    @Autowired
    public TokenService(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
    }

    public String build(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(Date.from(Instant.now().plus(tokenConfiguration.getExpirationMinutes(), ChronoUnit.MINUTES)))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenConfiguration.getSecret().getBytes())
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .setAllowedClockSkewSeconds(60)
                .setSigningKey(tokenConfiguration.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

}
