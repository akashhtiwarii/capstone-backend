package com.capstone.orders_service.feignClient;

import com.capstone.orders_service.dto.FoodItemOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "restaurants-service", url = "http://localhost:8080/restaurant")
public interface RestaurantFeignClient {
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantOutDTO> getRestaurantById(@PathVariable("restaurantId") long restaurantId);
    @GetMapping("/owner")
    public ResponseEntity<List<RestaurantOutDTO>> getRestaurantByOwnerId(@RequestParam long ownerId);
    @GetMapping("/restaurantfood/{restaurantId}")
    public ResponseEntity<List<FoodItemOutDTO>> getFoodItemsByRestaurant(@PathVariable("restaurantId") long restaurantId);
    @GetMapping("/food/id")
    public ResponseEntity<FoodItemOutDTO> getFoodItemById(@RequestParam long foodId);
}
