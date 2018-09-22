package ca.smartlibrary.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ScopeAuthority implements GrantedAuthority {
    PROFILE,
    LIBRARY;

    public static final String PROFILE_VALUE = "PROFILE";
    public static final String LIBRARY_VALUE = "LIBRARY";

    public static List<ScopeAuthority> all() {
        return Arrays.stream(values()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.name();
    }
}
