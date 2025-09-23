package com.gbill.getallfinalconsumerbill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Añadida la anotación @Configuration
public class Config {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer(){ 
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**") 
                .allowedOrigins("http://localhost:4200","http://37.60.243.227:4200")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") 
                .allowCredentials(true);
            }
        };
    }

}
