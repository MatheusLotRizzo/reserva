package com.fiap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reserva")
                        .version("v1")
                        .description("API de Reservas criada exclusivamente para o TechChallenge 3 da FIAP.")
                );
    }
}
