package com.capstone.orders_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) settings
 * for the entire application.
 */
@Configuration
public class CorsConfig {

    /**
     * Configures the CORS mappings for the application.
     *
     * @return a {@link WebMvcConfigurer} that defines the CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Defines the CORS settings for the application, including allowed origins,
             * HTTP methods, headers, and credentials.
             *
             * @param registry the {@link CorsRegistry} used to configure CORS mappings.
             */
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
