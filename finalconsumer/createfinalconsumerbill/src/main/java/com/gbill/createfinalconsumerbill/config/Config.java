package com.gbill.createfinalconsumerbill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Dominios permitidos (no se puede usar '*')
                        .allowedOrigins(
                            "http://localhost:4200",
                            "http://127.0.0.1:4200",
                            "https://bill.beckysflorist.site"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        // Permitir credenciales (tokens, cookies, etc.)
                        .allowCredentials(true);
            }
        };
    }
}
