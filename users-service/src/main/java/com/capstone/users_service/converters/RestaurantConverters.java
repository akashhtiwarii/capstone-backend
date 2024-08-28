package com.capstone.users_service.converters;

import com.capstone.users_service.InDTO.RestaurantInDTO;
import com.capstone.users_service.entity.Restaurant;

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
        if (restaurantInDTO.getImage() != null) {
            restaurant.setImage(restaurantInDTO.getImage());
        }
        return restaurant;
    }
}
