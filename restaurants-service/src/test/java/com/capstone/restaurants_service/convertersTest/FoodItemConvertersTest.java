package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


class FoodItemConvertersTest {

    @Test
    void testFoodItemInDTOToFoodItemEntity_WithValidData() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1);
        foodItemInDTO.setName("burger");
        foodItemInDTO.setDescription("Delicious beef burger");
        foodItemInDTO.setPrice(5.99);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(1, foodItem.getCategoryId());
        assertEquals("Burger", foodItem.getName());  // Capitalized
        assertEquals("Delicious beef burger", foodItem.getDescription());
        assertEquals(5.99, foodItem.getPrice());
    }

    @Test
    void testFoodItemInDTOToFoodItemEntity_WithNullDescription() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(2);
        foodItemInDTO.setName("pizza");
        foodItemInDTO.setDescription(null);
        foodItemInDTO.setPrice(8.50);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(2, foodItem.getCategoryId());
        assertEquals("Pizza", foodItem.getName());  // Capitalized
        assertEquals("", foodItem.getDescription());  // Null description should be empty string
        assertEquals(8.50, foodItem.getPrice());
    }

    @Test
    void testFoodItemInDTOToFoodItemEntity_WithEmptyDescription() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(3);
        foodItemInDTO.setName("sushi");
        foodItemInDTO.setDescription("");
        foodItemInDTO.setPrice(12.00);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(3, foodItem.getCategoryId());
        assertEquals("Sushi", foodItem.getName());  // Capitalized
        assertEquals("", foodItem.getDescription());  // Empty string should remain empty
        assertEquals(12.00, foodItem.getPrice());
    }

    @Test
    void testFoodItemInDTOToFoodItemEntity_WithNullName() {

        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(4);
        foodItemInDTO.setName(null);
        foodItemInDTO.setDescription("Grilled chicken");
        foodItemInDTO.setPrice(7.99);

        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);

        assertNotNull(foodItem);
        assertEquals(4, foodItem.getCategoryId());
        assertNull(foodItem.getName());  // Null name should remain null
        assertEquals("Grilled chicken", foodItem.getDescription());
        assertEquals(7.99, foodItem.getPrice());
    }
}