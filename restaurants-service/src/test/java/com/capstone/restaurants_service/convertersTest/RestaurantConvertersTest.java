package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.entity.Restaurant;
import dto.InDTO.RestaurantInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantConvertersTest {

    @Test
    void testRestaurantInDTOTORestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setName("Test Restaurant");
        restaurantInDTO.setEmail("test@example.com");
        restaurantInDTO.setPhone("123-456-7890");
        restaurantInDTO.setAddress("123 Test St");
        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);
        assertEquals(restaurantInDTO.getOwnerId(), restaurant.getOwnerId());
        assertEquals(restaurantInDTO.getName(), restaurant.getName());
        assertEquals(restaurantInDTO.getEmail(), restaurant.getEmail());
        assertEquals(restaurantInDTO.getPhone(), restaurant.getPhone());
        assertEquals(restaurantInDTO.getAddress(), restaurant.getAddress());
    }
}
