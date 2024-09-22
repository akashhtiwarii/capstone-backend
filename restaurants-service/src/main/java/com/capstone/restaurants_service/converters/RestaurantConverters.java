package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;

/**
 * Utility class for converting between {@link RestaurantInDTO} and {@link Restaurant} entities.
 * This class contains methods to transform {@link RestaurantInDTO} objects into {@link Restaurant} entities.
 */
public final class RestaurantConverters {
    /**
     * Private constructor for CategoryConverters.
     */
    private RestaurantConverters() {
        throw new UnsupportedOperationException("Utility Class Cannot be instantiated");
    }

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
    public static Restaurant restaurantInDTOTORestaurant(final RestaurantInDTO restaurantInDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(restaurantInDTO.getOwnerId());
        restaurant.setName(restaurantInDTO.getName() != null ? restaurantInDTO.getName().trim() : null);
        restaurant.setEmail(restaurantInDTO.getEmail() != null
                ? restaurantInDTO.getEmail().trim().toLowerCase() : null);
        restaurant.setPhone(restaurantInDTO.getPhone());
        restaurant.setAddress(restaurantInDTO.getAddress() != null ? restaurantInDTO.getAddress().trim() : null);
        return restaurant;
    }
}
