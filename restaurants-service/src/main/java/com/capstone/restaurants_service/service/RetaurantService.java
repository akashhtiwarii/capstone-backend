package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.entity.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RetaurantService {
    /**
     * Get all restaurants.
     * @return all restaurants
     */
    List<Restaurant> findAll();

    /**
     * Get Restaurant with a specific name.
     * @param name for filtering.
     * @return restaurant with a specific name
     */
    Restaurant findByName(String name);
    /**
     * Get Restaurant with a specific restaurantId.
     * @param restaurantId for filtering.
     * @return restaurant with a specific restaurantId
     */
    Restaurant findById(long restaurantId);

    /**
     * Save a new restaurant to the database.
     * @param restaurant for adding
     * @return a string with a message
     */
    ResponseEntity<String> save(Restaurant restaurant);
}
