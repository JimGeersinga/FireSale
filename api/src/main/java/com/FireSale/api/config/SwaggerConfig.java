package com.FireSale.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@Configuration
public class SwaggerConfig {
    public static final Contact CONTACT = new Contact("Astrid-HR, Carlo Ligthart, Jim Geersinga, Mike van Leeuwen, 0548643,", "https://github.com/mikevanl/FireSale", "");
    public static final ApiInfo DEFAULT_API = new ApiInfo("swagger", "Swagger Documentation", "1.0", "urn:tos", CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
    public static final Set<String> consumes = new HashSet<String>(Arrays.asList("application/json"));
    public static final Set<String> produces = new HashSet<String>(Arrays.asList("application/json"));
    public static final List<SecurityScheme> securitySchemes = Arrays.asList(new BasicAuth("basicAuth"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemes)
                .apiInfo(DEFAULT_API)
                .consumes(consumes)
                .produces(produces)
                .securityContexts(Arrays.asList(securityContexts()))
                .securitySchemes(Arrays.asList(basicAuthScheme()));
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(basicAuthReference()))
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
}
