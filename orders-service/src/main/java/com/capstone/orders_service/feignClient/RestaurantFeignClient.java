package com.capstone.orders_service.feignClient;

import com.capstone.orders_service.dto.FoodItemOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign Client interface for communicating with the restaurant service.
 * <p>
 * This interface defines methods for accessing restaurant-related data from the restaurant service using Feign,
 * which is a declarative HTTP client for Spring Boot. It allows you to make HTTP requests to the restaurant service
 * and retrieve information about restaurants and food items.
 * </p>
 */
@FeignClient(name = "restaurants-service", url = "http://localhost:8080/restaurant")
public interface RestaurantFeignClient {

    /**
     * Retrieves details of a restaurant by its unique identifier.
     *
     * @param restaurantId The unique identifier of the restaurant to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link RestaurantOutDTO} object with the restaurant details
     * or an error response if the restaurant is not found.
     */
    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantOutDTO> getRestaurantById(@PathVariable("restaurantId") long restaurantId);

    /**
     * Retrieves a list of restaurants owned by a specific owner.
     *
     * @param ownerId The unique identifier of the owner whose restaurants are to be retrieved.
     * @return A {@link ResponseEntity} containing a
     * {@link List} of {@link RestaurantOutDTO} objects with the restaurant
     * details or an error response if no restaurants are found for the owner.
     */
    @GetMapping("/owner")
    ResponseEntity<List<RestaurantOutDTO>> getRestaurantByOwnerId(@RequestParam long ownerId);

    /**
     * Retrieves a list of food items available at a specific restaurant.
     *
     * @param restaurantId The unique identifier of the restaurant whose food items are to be retrieved.
     * @return A {@link ResponseEntity} containing a {@link List} of {@link FoodItemOutDTO} objects with the food item
     *         details or an error response if no food items are found for the restaurant.
     */
    @GetMapping("/restaurantfood/{restaurantId}")
    ResponseEntity<List<FoodItemOutDTO>> getFoodItemsByRestaurant(@PathVariable("restaurantId") long restaurantId);

    /**
     * Retrieves details of a food item by its unique identifier.
     *
     * @param foodId The unique identifier of the food item to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link FoodItemOutDTO} object with the food item details
     *         or an error response if the food item is not found.
     */
    @GetMapping("/food/id")
    ResponseEntity<FoodItemOutDTO> getFoodItemById(@RequestParam long foodId);
}
