package iuh.fit.fashionecommercewebsitebackend.configs.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI getOpenApiDocumentation() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Fashion E-Commerce API")
                        .description("API for Fashion E-Commerce Website")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Fashion Sales").email("trungthinh2k2@gmail.com")))
                .externalDocs(new ExternalDocumentation().description("Fashion E-Commerce API Documentation").url("https://fashionsales.com"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", createBearerAuthScheme()));
    }

    private SecurityScheme createBearerAuthScheme() {
        return new SecurityScheme().name("bearerAuth")
                .description("JWT auth description")
                .scheme("bearer")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }

}