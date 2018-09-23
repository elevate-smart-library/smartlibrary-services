package ca.smartlibrary.configuration;


import ca.smartlibrary.security.AuthenticationFilter;
import ca.smartlibrary.security.ScopeVoter;
import ca.smartlibrary.service.TokenService;
import ca.smartlibrary.service.user.UserService;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static java.lang.String.format;

@Configuration
@ConfigurationProperties(prefix = "security")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("methodSecurityInterceptor")
    private MethodInterceptor methodSecurityInterceptor;


    @PostConstruct
    public void init() {
        // Set custom Security interceptor voter
        if (!(methodSecurityInterceptor instanceof MethodSecurityInterceptor)) {
            throw new IllegalStateException(format("Method security interceptor is of: %s, can't register custom voter", methodSecurityInterceptor.getClass().getSimpleName()));
        }

        AccessDecisionManager decisionManager = ((MethodSecurityInterceptor) methodSecurityInterceptor).getAccessDecisionManager();
        if (!(decisionManager instanceof AbstractAccessDecisionManager)) {
            throw new IllegalStateException(format("Access decision manager is of: %s, can't register custom voter", decisionManager.getClass().getSimpleName()));
        }
        ((AbstractAccessDecisionManager) decisionManager).getDecisionVoters().add(new ScopeVoter());
    }

    //    @Bean // TODO: 2018-09-22
    public FilterChainProxy configureAccessFilters() {
        return new FilterChainProxy(Arrays.asList(
                new DefaultSecurityFilterChain(new RegexRequestMatcher("/api/v\\d/auth/.*", null)),
                new DefaultSecurityFilterChain(new AntPathRequestMatcher("/api/**"),
                        new AuthenticationFilter(tokenService, userService))
        ));
    }

    @Configuration
    public class WebAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .cors().disable()
//                    .cors().configurationSource(new UrlBasedCorsConfigurationSource()).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
}
