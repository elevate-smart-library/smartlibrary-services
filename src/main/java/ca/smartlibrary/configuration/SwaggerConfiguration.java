package ca.smartlibrary.configuration;

import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.or;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private TokenConfiguration tokenConfiguration;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(of(authorizationHeader()).collect(toList()))
                .apiInfo(apiInfo())
                .select().paths(or(regex("/api/.*"))).build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .defaultModelRendering(ModelRendering.MODEL)
                .displayRequestDuration(true)
                .filter(true)
                .operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

    private Parameter authorizationHeader() {
        return new ParameterBuilder()
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .name(tokenConfiguration.getHeaderName())
                .required(false)
                .build();
    }

    private ApiInfo apiInfo() {
        String description = "";
        try (InputStream stream = new ClassPathResource("swagger/api-description.html").getInputStream()) {
            description = new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .collect(Collectors.joining("\n"));

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return new ApiInfoBuilder()
                .title("Little Library API")
                .description(description)
                .version("1.0")
                .build();
    }
}