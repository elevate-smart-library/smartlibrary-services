package ca.smartlibrary.controller;

import ca.smartlibrary.security.TokenAuthentication;
import ca.smartlibrary.service.AuthenticationService;
import ca.smartlibrary.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static java.util.Objects.nonNull;

@RestControllerAdvice("ca.smartlibrary")
public class TokenAdvice implements ResponseBodyAdvice<Object> {

    private final TokenService tokenService;

    @Autowired
    public TokenAdvice(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String authorizationHeaderName = tokenService.getTokenConfiguration().getHeaderName();
        if (nonNull(serverHttpResponse.getHeaders().getFirst(authorizationHeaderName))) {
            throw new RuntimeException("Only TokenAdvice can update authorization header");
        }

        TokenAuthentication authentication = AuthenticationService.getTokenAuthentication();
        if (nonNull(authentication)) {
            String token = tokenService.build(authentication.getPrincipal(), authentication.getCredentials());
            serverHttpResponse.getHeaders().set(authorizationHeaderName, token);
        }
        return body;
    }

}
