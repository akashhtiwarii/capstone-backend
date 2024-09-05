package com.capstone.orders_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "restaurants-service", url = "http://localhost:8080/restaurant")
public interface RestaurantFeignClient {
}
