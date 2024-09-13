package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;

/**
 * Utility class for converting between {@link RestaurantInDTO} and {@link Restaurant} entities.
 * This class contains methods to transform {@link RestaurantInDTO} objects into {@link Restaurant} entities.
 */
public class RestaurantConverters {

    /**
     * Converts a {@link RestaurantInDTO} object to a {@link Restaurant} entity.
     * <p>
     * This method maps the fields from the provided DTO to the corresponding fields in the {@link Restaurant} entity:
     * <ul>
     *     <li>Owner ID from the DTO to the entity.</li>
     *     <li>Name from the DTO to the entity.</li>
     *     <li>Email from the DTO to the entity.</li>
     *     <li>Phone number from the DTO to the entity.</li>
     *     <li>Address from the DTO to the entity.</li>
     * </ul>
     * </p>
     *
     * @param restaurantInDTO the DTO containing restaurant data to be converted
     * @return a {@link Restaurant} entity populated with data from the provided DTO
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
