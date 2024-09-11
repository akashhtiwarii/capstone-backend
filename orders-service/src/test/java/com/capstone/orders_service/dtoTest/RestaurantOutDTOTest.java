package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.RestaurantOutDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

public class RestaurantOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();

        assertEquals(0, restaurantOutDTO.getRestaurantId());
        long restaurantId = 1001L;
        restaurantOutDTO.setRestaurantId(restaurantId);
        assertEquals(restaurantId, restaurantOutDTO.getRestaurantId());

        assertEquals(0, restaurantOutDTO.getOwnerId());
        long ownerId = 2002L;
        restaurantOutDTO.setOwnerId(ownerId);
        assertEquals(ownerId, restaurantOutDTO.getOwnerId());

        assertNull(restaurantOutDTO.getName());
        String name = "Test Restaurant";
        restaurantOutDTO.setName(name);
        assertEquals(name, restaurantOutDTO.getName());

        assertNull(restaurantOutDTO.getEmail());
        String email = "test@restaurant.com";
        restaurantOutDTO.setEmail(email);
        assertEquals(email, restaurantOutDTO.getEmail());

        assertNull(restaurantOutDTO.getPhone());
        String phone = "1234567890";
        restaurantOutDTO.setPhone(phone);
        assertEquals(phone, restaurantOutDTO.getPhone());

        assertNull(restaurantOutDTO.getImage());
        byte[] image = new byte[] { 1, 2, 3 };
        restaurantOutDTO.setImage(image);
        assertArrayEquals(image, restaurantOutDTO.getImage());
    }

    @Test
    public void testEqualsAndHashcode() {
        long restaurantId = 1001L;
        long ownerId = 2002L;
        String name = "Test Restaurant";
        String email = "test@restaurant.com";
        String phone = "1234567890";
        byte[] image = new byte[] { 1, 2, 3 };

        RestaurantOutDTO dto1 = buildRestaurantOutDTO(restaurantId, ownerId, name, email, phone, image);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        RestaurantOutDTO dto2 = buildRestaurantOutDTO(restaurantId, ownerId, name, email, phone, image);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId + 1, ownerId, name, email, phone, image);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId, ownerId + 1, name, email, phone, image);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId, ownerId, name + " Updated", email, phone, image);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId, ownerId, name, email + " Updated", phone, image);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId, ownerId, name, email, phone + " Updated", image);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOutDTO(restaurantId, ownerId, name, email, phone, new byte[] { 4, 5, 6 });
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new RestaurantOutDTO();
        dto2 = new RestaurantOutDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    private RestaurantOutDTO buildRestaurantOutDTO(long restaurantId, long ownerId, String name, String email, String phone, byte[] image) {
        RestaurantOutDTO dto = new RestaurantOutDTO();

        dto.setRestaurantId(restaurantId);
        dto.setOwnerId(ownerId);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setImage(image);

        return dto;
    }
}

