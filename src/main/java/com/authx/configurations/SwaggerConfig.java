package com.authx.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {
        return new OpenAPI()
                .info(
                        new Info().title("AuthX-Jwt Authentication APIs")
                                .description("By Rustam Poonia"))

                .servers(List.of(new Server().url("http://localhost:8080/api/v1.0").description("Local"),
                        new Server().url("").description("Live")))
                        
                .tags(List.of(
                    new Tag().name("Profile APIs"),
                    new Tag().name("User APIs")
                ));
    }
}
