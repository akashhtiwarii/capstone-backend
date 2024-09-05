package com.capstone.users_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application Entrypoint.
 */
@SpringBootApplication
@EnableFeignClients
public class UsersServiceApplication {
     /**
	 * main method.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

}
