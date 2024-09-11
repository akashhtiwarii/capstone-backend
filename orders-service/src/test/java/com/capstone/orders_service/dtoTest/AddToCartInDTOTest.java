package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.AddToCartInDTO;
import org.junit.jupiter.api.Test;

public class AddToCartInDTOTest {

    @Test
    public void testGetterAndSetter() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();

        assertEquals(0, addToCartInDTO.getUserId());
        long userId = 1L;
        addToCartInDTO.setUserId(userId);
        assertEquals(userId, addToCartInDTO.getUserId());

        assertEquals(0, addToCartInDTO.getRestaurantId());
        long restaurantId = 2L;
        addToCartInDTO.setRestaurantId(restaurantId);
        assertEquals(restaurantId, addToCartInDTO.getRestaurantId());

        assertEquals(0, addToCartInDTO.getFoodId());
        long foodId = 3L;
        addToCartInDTO.setFoodId(foodId);
        assertEquals(foodId, addToCartInDTO.getFoodId());

        assertEquals(0, addToCartInDTO.getQuantity());
        int quantity = 5;
        addToCartInDTO.setQuantity(quantity);
        assertEquals(quantity, addToCartInDTO.getQuantity());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 1L;
        long restaurantId = 2L;
        long foodId = 3L;
        int quantity = 5;

        AddToCartInDTO addToCartInDTO1 = buildAddToCartInDTO(userId, restaurantId, foodId, quantity);

        assertEquals(addToCartInDTO1, addToCartInDTO1);
        assertEquals(addToCartInDTO1.hashCode(), addToCartInDTO1.hashCode());

        assertNotEquals(addToCartInDTO1, new Object());

        AddToCartInDTO addToCartInDTO2 = buildAddToCartInDTO(userId, restaurantId, foodId, quantity);
        assertEquals(addToCartInDTO1, addToCartInDTO2);
        assertEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());

        addToCartInDTO2 = buildAddToCartInDTO(userId + 1, restaurantId, foodId, quantity);
        assertNotEquals(addToCartInDTO1, addToCartInDTO2);
        assertNotEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());

        addToCartInDTO2 = buildAddToCartInDTO(userId, restaurantId + 1, foodId, quantity);
        assertNotEquals(addToCartInDTO1, addToCartInDTO2);
        assertNotEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());

        addToCartInDTO2 = buildAddToCartInDTO(userId, restaurantId, foodId + 1, quantity);
        assertNotEquals(addToCartInDTO1, addToCartInDTO2);
        assertNotEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());

        addToCartInDTO2 = buildAddToCartInDTO(userId, restaurantId, foodId, quantity + 1);
        assertNotEquals(addToCartInDTO1, addToCartInDTO2);
        assertNotEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());

        addToCartInDTO1 = new AddToCartInDTO();
        addToCartInDTO2 = new AddToCartInDTO();
        assertEquals(addToCartInDTO1, addToCartInDTO2);
        assertEquals(addToCartInDTO1.hashCode(), addToCartInDTO2.hashCode());
    }

    private AddToCartInDTO buildAddToCartInDTO(long userId, long restaurantId, long foodId, int quantity) {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();

        addToCartInDTO.setUserId(userId);
        addToCartInDTO.setRestaurantId(restaurantId);
        addToCartInDTO.setFoodId(foodId);
        addToCartInDTO.setQuantity(quantity);

        return addToCartInDTO;
    }
}
