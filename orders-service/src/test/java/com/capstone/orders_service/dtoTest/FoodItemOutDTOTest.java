package com.capstone.orders_service.dtoTest;

import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.FoodItemOutDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FoodItemOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();

        assertEquals(0, foodItemOutDTO.getFoodId());
        long foodId = 1L;
        foodItemOutDTO.setFoodId(foodId);
        assertEquals(foodId, foodItemOutDTO.getFoodId());

        assertEquals(0, foodItemOutDTO.getCategoryId());
        long categoryId = 2L;
        foodItemOutDTO.setCategoryId(categoryId);
        assertEquals(categoryId, foodItemOutDTO.getCategoryId());

        assertNull(foodItemOutDTO.getName());
        String name = "Food Item";
        foodItemOutDTO.setName(name);
        assertEquals(name, foodItemOutDTO.getName());

        assertNull(foodItemOutDTO.getDescription());
        String description = "Description of the food item";
        foodItemOutDTO.setDescription(description);
        assertEquals(description, foodItemOutDTO.getDescription());

        assertEquals(0.0, foodItemOutDTO.getPrice());
        double price = 12.99;
        foodItemOutDTO.setPrice(price);
        assertEquals(price, foodItemOutDTO.getPrice(), 0.001);

        assertNull(foodItemOutDTO.getImage());
        byte[] image = {1, 2, 3};
        foodItemOutDTO.setImage(image);
        assertArrayEquals(image, foodItemOutDTO.getImage());
    }

    @Test
    public void testEqualsAndHashcode() {
        long foodId = 1L;
        long categoryId = 2L;
        String name = "Food Item";
        String description = "Description of the food item";
        double price = 12.99;
        byte[] image = {1, 2, 3};

        FoodItemOutDTO foodItemOutDTO1 = buildFoodItemOutDTO(foodId, categoryId, name, description, price, image);

        assertEquals(foodItemOutDTO1, foodItemOutDTO1);
        assertEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO1.hashCode());

        assertNotEquals(foodItemOutDTO1, new Object());

        FoodItemOutDTO foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId, name, description, price, image);
        assertEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId + 1, categoryId, name, description, price, image);
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId + 1, name, description, price, image);
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId, "Different Food", description, price, image);
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId, name, "Different Description", price, image);
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId, name, description, price + 1.0, image);
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2 = buildFoodItemOutDTO(foodId, categoryId, name, description, price, new byte[]{4, 5, 6});
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO1 = new FoodItemOutDTO();
        foodItemOutDTO2 = new FoodItemOutDTO();
        assertEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());
    }

    private FoodItemOutDTO buildFoodItemOutDTO(long foodId, long categoryId, String name, String description, double price, byte[] image) {
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();

        foodItemOutDTO.setFoodId(foodId);
        foodItemOutDTO.setCategoryId(categoryId);
        foodItemOutDTO.setName(name);
        foodItemOutDTO.setDescription(description);
        foodItemOutDTO.setPrice(price);
        foodItemOutDTO.setImage(image);

        return foodItemOutDTO;
    }
}

