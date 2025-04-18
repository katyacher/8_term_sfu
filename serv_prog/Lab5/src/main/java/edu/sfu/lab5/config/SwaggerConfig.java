package edu.sfu.lab5.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI jewelryShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jewelry Shop API")
                        .description("API для управления ювелирными изделиями")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Поддержка")
                                .email("support@jewelryshop.com")
                                .url("https://jewelryshop.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}