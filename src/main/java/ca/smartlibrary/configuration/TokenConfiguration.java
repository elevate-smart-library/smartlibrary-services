package ca.smartlibrary.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.token")
public class TokenConfiguration {

    private String headerName;
    private String secret;
    private int expirationMinutes;

}
