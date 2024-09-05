package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;

/**
 * Restaurant In DTOs to Entity Converters and Vice Versa.
 */
public class RestaurantConverters {

    /**
     * Restaurant In DTO to Restaurant.
     * @param restaurantInDTO object
     * @return Restaurant
     */
    public static Restaurant restaurantInDTOTORestaurant(RestaurantInDTO restaurantInDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(restaurantInDTO.getOwnerId());
        restaurant.setName(restaurantInDTO.getName());
        restaurant.setEmail(restaurantInDTO.getEmail());
        restaurant.setPhone(restaurantInDTO.getPhone());
        restaurant.setAddress(restaurantInDTO.getAddress());
        return restaurant;
    }
}

