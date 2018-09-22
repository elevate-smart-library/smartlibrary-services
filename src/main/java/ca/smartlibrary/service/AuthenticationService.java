package ca.smartlibrary.service;

import ca.smartlibrary.dto.User;
import ca.smartlibrary.security.TokenAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface AuthenticationService {

    User register(User user) throws Exception;

    static TokenAuthentication getRequiredTokenAuthentication() {
        return requireNonNull(getTokenAuthentication(), "Authentication cannot be null this point");
    }

    static TokenAuthentication getTokenAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof TokenAuthentication ? (TokenAuthentication) authentication : null;
    }

    static User getAuthenticatedUser() {
        TokenAuthentication authentication = getTokenAuthentication();
        return Objects.isNull(authentication) ? null : authentication.getUser();
    }
}

