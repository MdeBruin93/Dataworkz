package com.dataworks.eventsubscriber;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title("DataWorkz EventSubscriber API")
                                .description("This RESTfull API of DataWorkz is using springdoc-openapi and OpenAPI 3.")
                                .contact(
                                        new Contact()
                                                .name("DataWorkz")
                                                .email("0947381@hr.nl")
                                                .url("https://www.hogeschoolrotterdam.nl")
                                )
                                .version("1.0")
                );
    }
}
