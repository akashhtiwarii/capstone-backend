package com.capstone.users_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point for the Users Service application.
 * <p>
 * This class serves as the entry point for the Spring Boot application, initializing the Spring context and
 * enabling Feign clients for inter-service communication.
 * </p>
 */
@SpringBootApplication
@EnableFeignClients
public class UsersServiceApplication {
     /**
      * Runs the application.
      *
      * @param args Arguments parameter.
      */
     public final void run(final String[] args) {
          SpringApplication.run(UsersServiceApplication.class, args);
     }
     /**
     * Main method to start the Users Service application.
     * <p>
     * This method launches the Spring Boot application by invoking {@link SpringApplication#run(Class, String[])}
     * and initializes the application context.
     * </p>
     *
     * @param args command-line arguments that can be used to customize the application's startup behavior.
     */
     public static void main(final String[] args) {
          SpringApplication.run(UsersServiceApplication.class, args);
     }
}
