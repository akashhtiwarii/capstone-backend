package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.entity.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantConvertersTest {

    @Test
    void testRestaurantInDTOTORestaurant() {
        byte[] byteArray = new byte[]{1, 2, 3, 4, 5};
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setName("Test Restaurant");
        restaurantInDTO.setEmail("test@restaurant.com");
        restaurantInDTO.setPhone("1234567890");
        restaurantInDTO.setAddress("123 Test St");
        restaurantInDTO.setImage(byteArray);

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertEquals(1L, restaurant.getOwnerId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("test@restaurant.com", restaurant.getEmail());
        assertEquals("1234567890", restaurant.getPhone());
        assertEquals("123 Test St", restaurant.getAddress());
        assertEquals(byteArray, restaurant.getImage());
    }

    @Test
    void testRestaurantInDTOWithoutImage() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setName("Test Restaurant");
        restaurantInDTO.setEmail("test@restaurant.com");
        restaurantInDTO.setPhone("1234567890");
        restaurantInDTO.setAddress("123 Test St");
        restaurantInDTO.setImage(null);

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertEquals(1L, restaurant.getOwnerId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("test@restaurant.com", restaurant.getEmail());
        assertEquals("1234567890", restaurant.getPhone());
        assertEquals("123 Test St", restaurant.getAddress());
        assertEquals(null, restaurant.getImage());
    }
}

