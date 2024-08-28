package com.capstone.users_service.service;

import com.capstone.users_service.InDTO.RestaurantInDTO;
import com.capstone.users_service.entity.Restaurant;

import java.util.List;

/**
 * RestaurantService for defining methods related to restaurants table.
 */
public interface RestaurantService {

    /**
     * Add a new Restaurant.
     * @param restaurantInDTO request object
     * @return message after saving restaurant to database
     */
    String save(RestaurantInDTO restaurantInDTO);

    /**
     * Get All Restaurants.
     * @return the list of all restaurants
     */
    List<Restaurant> findAll();
}
