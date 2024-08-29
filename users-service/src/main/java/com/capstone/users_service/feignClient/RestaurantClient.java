package com.capstone.users_service.feignClient;

import com.capstone.users_service.InDTO.RestaurantInDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "restaurants-service", url = "http://localhost:8080")
public interface RestaurantClient {
    /**
     * Communicating with the restaurant service.
     * @param restaurantInDTO request body
     * @return String message
     */
    @PostMapping("/restaurant/add")
    ResponseEntity<String> addRestaurant(RestaurantInDTO restaurantInDTO);
}
