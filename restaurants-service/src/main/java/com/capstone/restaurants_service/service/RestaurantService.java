package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.dto.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * RestaurantService for defining methods related to restaurants table.
 */
public interface RestaurantService {

    /**
     * Add a new Restaurant.
     * @param restaurantInDTO request object
     * @param image
     * @return message after saving restaurant to database
     */
    String save(RestaurantInDTO restaurantInDTO, MultipartFile image);

    /**
     * Get All Restaurants.
     * @return the list of all restaurants
     */
    List<Restaurant> findAll();

    /**
     * Get Restaurant bu ownerId.
     * @param ownerId
     * @return List of restaurants
     */
    List<Restaurant> findByOwnerId(long ownerId);

    /**
     * Get Restaurant By Id.
     * @param restaurantId
     * @return Restaurant
     */
    Restaurant findById(long restaurantId);

    /**
     * Update Restaurant.
     * @param updateRestaurantInDTO
     * @return String message
     */
    String updateRestaurant(UpdateRestaurantInDTO updateRestaurantInDTO);
}
