package com.proxyproject.products_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productsOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Products Service API")
                        .description("Proxy to FakeStore API")
                        .version("v1"));
    }

    /**
     * Limit springdoc to scan only our controllers package and paths.
     * This avoids scanning leftover beans (e.g. ControllerAdvice) that can
     * cause /v3/api-docs failures.
     */
    @Bean
    public GroupedOpenApi productsGroup() {
        return GroupedOpenApi.builder()
                .group("products")
                .packagesToScan("com.proxyproject.products_service.controllers")
                .pathsToMatch("/api/products/**")
                .build();
    }
}
