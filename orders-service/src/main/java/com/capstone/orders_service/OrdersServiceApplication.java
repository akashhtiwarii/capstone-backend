package com.capstone.orders_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Orders Service Application.
 * This class is the entry point for the Spring Boot application, which is responsible
 * for bootstrapping and launching the application. The @SpringBootApplication annotation
 * enables autoconfiguration, component scanning, and configuration support.
 * The @EnableFeignClients annotation is used to scan for Feign clients, which allow
 * for seamless inter-service communication.
 */
@SpringBootApplication
@EnableFeignClients
public class OrdersServiceApplication {

    /**
    * Runs the application.
    *
    * @param args Arguments parameter.
    */
    public final void run(final String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    /**
     * Main method that serves as the entry point for the Spring Boot Orders Service application.
     *
     * @param args command-line arguments (if any) passed during application startup
     */
    public static void main(final String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

}
