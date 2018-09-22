package ca.smartlibrary.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static java.util.Objects.nonNull;

public class ScopeVoter implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object Object, Collection<ConfigAttribute> attributes) {
        return nonNull(authentication) &&
                authentication instanceof TokenAuthentication &&
                authContainsAttributes(authentication.getAuthorities(), attributes) ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    private boolean authContainsAttributes(Collection<? extends GrantedAuthority> authorities,
                                           Collection<ConfigAttribute> attributes) {
        return attributes.stream()
                .map(ConfigAttribute::getAttribute)
                .allMatch(attr -> authorities.stream().anyMatch(s -> s.getAuthority().equals(attr)));
    }
}
