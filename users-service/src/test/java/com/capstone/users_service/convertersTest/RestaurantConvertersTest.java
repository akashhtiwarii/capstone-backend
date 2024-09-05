package com.capstone.users_service.convertersTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantConvertersTest {

    @Test
    void testValidRestaurantInDTOTORestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setName("Sample Restaurant");
        restaurantInDTO.setEmail("sample@gmail.com");
        restaurantInDTO.setPhone("9234567890");
        restaurantInDTO.setAddress("Smriti Nagar");
        restaurantInDTO.setImage(new byte[]{1, 2, 3});

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertNotNull(restaurant);
        assertEquals(restaurantInDTO.getOwnerId(), restaurant.getOwnerId());
        assertEquals(restaurantInDTO.getName(), restaurant.getName());
        assertEquals(restaurantInDTO.getEmail(), restaurant.getEmail());
        assertEquals(restaurantInDTO.getPhone(), restaurant.getPhone());
        assertEquals(restaurantInDTO.getAddress(), restaurant.getAddress());
        assertArrayEquals(restaurantInDTO.getImage(), restaurant.getImage());
    }

    @Test
    void testNullImageRestaurantInDTOTORestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(2L);
        restaurantInDTO.setName("Sample Restaurant");
        restaurantInDTO.setEmail("sample@gmail.com");
        restaurantInDTO.setPhone("9987654321");
        restaurantInDTO.setAddress("Indira Market");
        restaurantInDTO.setImage(null);

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertNotNull(restaurant);
        assertEquals(restaurantInDTO.getOwnerId(), restaurant.getOwnerId());
        assertEquals(restaurantInDTO.getName(), restaurant.getName());
        assertEquals(restaurantInDTO.getEmail(), restaurant.getEmail());
        assertEquals(restaurantInDTO.getPhone(), restaurant.getPhone());
        assertEquals(restaurantInDTO.getAddress(), restaurant.getAddress());
        assertNull(restaurant.getImage());
    }

    @Test
    void testEmptyFieldsRestaurantInDTOTORestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(3L);
        restaurantInDTO.setName("");
        restaurantInDTO.setEmail("");
        restaurantInDTO.setPhone("");
        restaurantInDTO.setAddress("");
        restaurantInDTO.setImage(new byte[]{});

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);

        assertNotNull(restaurant);
        assertEquals(restaurantInDTO.getOwnerId(), restaurant.getOwnerId());
        assertEquals(restaurantInDTO.getName(), restaurant.getName());
        assertEquals(restaurantInDTO.getEmail(), restaurant.getEmail());
        assertEquals(restaurantInDTO.getPhone(), restaurant.getPhone());
        assertEquals(restaurantInDTO.getAddress(), restaurant.getAddress());
        assertArrayEquals(restaurantInDTO.getImage(), restaurant.getImage());
    }
}
