package org.example.confeitaria.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI confeitariaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Confeitaria")
                        .description("API para pedidos de confeitaria com status, MySQL, Flyway e Factory de itens")
                        .version("1.0.0")
                        .contact(new Contact().name("Confeitaria").email("contato@confeitaria.local")))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI")
                        .url("/swagger-ui.html"));
    }
}