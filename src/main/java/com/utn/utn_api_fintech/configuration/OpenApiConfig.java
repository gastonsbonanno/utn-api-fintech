package com.utn.utn_api_fintech.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UTN API Fintech")
                        .version("v0.0.1")
                        .description("API REST para el proyecto UTN Fintech - documentación automática con OpenAPI/Swagger")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                );
    }

}

