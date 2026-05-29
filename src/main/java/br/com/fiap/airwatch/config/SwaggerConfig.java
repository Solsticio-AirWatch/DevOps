package br.com.fiap.airwatch.config;
import io.swagger.v3.oas.models.*; import io.swagger.v3.oas.models.info.*; import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;
@Configuration
public class SwaggerConfig {
    @Bean public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title("AirWatch API").description("Air Quality Monitoring - Grupo Solsticio FIAP 2026").version("1.0.0")
                .contact(new Contact().name("Grupo Solsticio").email("solsticio@fiap.com.br")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
