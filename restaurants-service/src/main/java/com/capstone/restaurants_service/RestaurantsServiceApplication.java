package com.capstone.restaurants_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestaurantsServiceApplication {
    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantsServiceApplication.class, args);
    }

}
