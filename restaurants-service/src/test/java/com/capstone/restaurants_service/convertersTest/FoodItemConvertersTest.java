package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FoodItemConvertersTest {

    @Test
    void testFoodItemInDTOToFoodItemEntityWithValidData() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1);
        foodItemInDTO.setName("food");
        foodItemInDTO.setDescription("Description");
        foodItemInDTO.setPrice(5.99);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(1, foodItem.getCategoryId());
        assertEquals("Food", foodItem.getName());
        assertEquals("Description", foodItem.getDescription());
        assertEquals(5.99, foodItem.getPrice());
    }

    @Test
    void testFoodItemInDTOToFoodItemEntityWithNullDescription() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(2);
        foodItemInDTO.setName("food");
        foodItemInDTO.setDescription(null);
        foodItemInDTO.setPrice(8.50);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(2, foodItem.getCategoryId());
        assertEquals("Food", foodItem.getName());
        assertEquals("", foodItem.getDescription());
        assertEquals(8.50, foodItem.getPrice());
    }

    @Test
    void testFoodItemInDTOToFoodItemEntityWithEmptyDescription() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(3);
        foodItemInDTO.setName("food");
        foodItemInDTO.setDescription("");
        foodItemInDTO.setPrice(12.00);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(3, foodItem.getCategoryId());
        assertEquals("Food", foodItem.getName());
        assertEquals("", foodItem.getDescription());
        assertEquals(12.00, foodItem.getPrice());
    }
}