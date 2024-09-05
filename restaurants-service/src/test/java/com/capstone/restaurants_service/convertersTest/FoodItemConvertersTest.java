package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoodItemConvertersTest {

    @Test
    void testFoodItemInDTOToFoodItemEntity() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("test item");
        foodItemInDTO.setDescription("description");

        StringUtils stringUtils = new StringUtils();
        String capitalizedString = stringUtils.capitalizeFirstLetter(foodItemInDTO.getName());

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertEquals(1L, foodItem.getCategoryId());
        assertEquals(capitalizedString, foodItem.getName());
        assertEquals("description", foodItem.getDescription());
    }

    @Test
    void testFoodItemInDTOWithNullDescription() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("test item");
        foodItemInDTO.setDescription(null);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertEquals(1L, foodItem.getCategoryId());
        assertEquals("Test item", foodItem.getName());
        assertEquals("", foodItem.getDescription());
    }

    @Test
    void testFoodItemInDTOWithEmptyDescription() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("test item");
        foodItemInDTO.setDescription("");

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertEquals(1L, foodItem.getCategoryId());
        assertEquals("Test item", foodItem.getName());
        assertEquals("", foodItem.getDescription());
    }
}
