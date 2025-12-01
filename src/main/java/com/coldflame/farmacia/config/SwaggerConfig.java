package com.coldflame.farmacia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Farmacia - Sistema de Ventas")
                        .version("1.0")
                        .description("API REST para gestión de ventas de farmacia con control de stock y precios históricos"));
    }
}
