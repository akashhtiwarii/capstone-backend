package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.InDTO.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;

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

    /**
     * Get Restaurant bu ownerId.
     * @param getOwnerRestaurantsInDTO
     * @return List of restaurants
     */
    Restaurant findByOwnerId(GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO);

    /**
     * Get Restaurant By Id.
     * @param restaurantId
     * @return Restaurant
     */
    Restaurant findById(long restaurantId);
}

