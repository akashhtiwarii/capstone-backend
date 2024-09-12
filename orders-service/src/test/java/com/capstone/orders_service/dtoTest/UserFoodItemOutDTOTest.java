package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.UserFoodItemOutDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class UserFoodItemOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        UserFoodItemOutDTO userFoodItemOutDTO = new UserFoodItemOutDTO();

        assertEquals(0, userFoodItemOutDTO.getFoodId());
        long foodId = 3003L;
        userFoodItemOutDTO.setFoodId(foodId);
        assertEquals(foodId, userFoodItemOutDTO.getFoodId());

        assertNull(userFoodItemOutDTO.getName());
        String name = "Test Food Item";
        userFoodItemOutDTO.setName(name);
        assertEquals(name, userFoodItemOutDTO.getName());

        assertEquals(0, userFoodItemOutDTO.getQuantity());
        int quantity = 10;
        userFoodItemOutDTO.setQuantity(quantity);
        assertEquals(quantity, userFoodItemOutDTO.getQuantity());

        assertEquals(0.0, userFoodItemOutDTO.getPrice());
        double price = 99.99;
        userFoodItemOutDTO.setPrice(price);
        assertEquals(price, userFoodItemOutDTO.getPrice());
    }

    @Test
    public void testEqualsAndHashcode() {
        long foodId = 3003L;
        String name = "Test Food Item";
        int quantity = 10;
        double price = 99.99;

        UserFoodItemOutDTO dto1 = buildUserFoodItemOutDTO(foodId, name, quantity, price);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        UserFoodItemOutDTO dto2 = buildUserFoodItemOutDTO(foodId, name, quantity, price);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserFoodItemOutDTO(foodId + 1, name, quantity, price);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserFoodItemOutDTO(foodId, name + " Updated", quantity, price);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserFoodItemOutDTO(foodId, name, quantity + 1, price);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserFoodItemOutDTO(foodId, name, quantity, price + 1);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new UserFoodItemOutDTO();
        dto2 = new UserFoodItemOutDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    private UserFoodItemOutDTO buildUserFoodItemOutDTO(long foodId, String name, int quantity, double price) {
        UserFoodItemOutDTO dto = new UserFoodItemOutDTO();

        dto.setFoodId(foodId);
        dto.setName(name);
        dto.setQuantity(quantity);
        dto.setPrice(price);

        return dto;
    }
}

