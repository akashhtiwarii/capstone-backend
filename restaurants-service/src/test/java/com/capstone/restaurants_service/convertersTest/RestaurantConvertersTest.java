package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantConvertersTest {

    @Test
    public void testRestaurantInDTOTORestaurant() {

        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setName("Test Restaurant");
        restaurantInDTO.setEmail("test@example.com");
        restaurantInDTO.setPhone("1234567890");
        restaurantInDTO.setAddress("address");

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertNotNull(restaurant);
        assertEquals(1L, restaurant.getOwnerId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("test@example.com", restaurant.getEmail());
        assertEquals("1234567890", restaurant.getPhone());
        assertEquals("address", restaurant.getAddress());
    }

    @Test
    public void testRestaurantInDTOTORestaurantWithPartialFields() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(2L);
        restaurantInDTO.setName("Another Restaurant");
        restaurantInDTO.setEmail(null);
        restaurantInDTO.setPhone("987-654-3210");
        restaurantInDTO.setAddress(null);

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertNotNull(restaurant);
        assertEquals(2L, restaurant.getOwnerId());
        assertEquals("Another Restaurant", restaurant.getName());
        assertNull(restaurant.getEmail());
        assertEquals("987-654-3210", restaurant.getPhone());
        assertNull(restaurant.getAddress());
    }
}