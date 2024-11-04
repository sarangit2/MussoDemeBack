package com.kalanso.mussoback.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/images/**")
                .allowedOrigins("http://localhost:55015")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");


        // Nouvel endpoint pour les API
        registry.addMapping("/api/**") // Ajoutez cet endpoint
                .allowedOrigins("http://localhost:55015, http://localhost:4200") // Autorisez les origines spécifiques
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
                .allowedHeaders("*"); // Autoriser tous les headers
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:images/");
    }
}
