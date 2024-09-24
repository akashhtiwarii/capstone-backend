package com.capstone.restaurants_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point for the Restaurants Service application.
 * <p>
 * This class is responsible for starting the Spring Boot application and enabling Feign clients for communication
 * with other microservices.
 * </p>
 */
@SpringBootApplication
@EnableFeignClients
public class RestaurantsServiceApplication {

    /**
     * The main method which serves as the entry point for the Java application.
     * <p>
     * This method triggers the Spring Boot application startup and initializes the application context.
     * </p>
     *
     * @param args command-line arguments passed to the application. Can be used to provide configuration
     *             or arguments to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantsServiceApplication.class, args);
    }
}
