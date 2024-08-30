package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FoodItemInDTOTest {

    @Test
    void testGettersAndSetters() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("Burger");
        foodItemInDTO.setDescription("Delicious beef burger");
        foodItemInDTO.setPrice(9.99);
        foodItemInDTO.setImage(new byte[]{1, 2, 3});

        assertEquals(1L, foodItemInDTO.getCategoryId());
        assertEquals("Burger", foodItemInDTO.getName());
        assertEquals("Delicious beef burger", foodItemInDTO.getDescription());
        assertEquals(9.99, foodItemInDTO.getPrice());
        assertEquals(Arrays.toString(new byte[]{1, 2, 3}), Arrays.toString(foodItemInDTO.getImage()));
    }

    @Test
    void testEqualsAndHashCode() {
        FoodItemInDTO foodItem1 = new FoodItemInDTO(1L, "Burger", "Delicious beef burger", 9.99, new byte[]{1, 2, 3});
        FoodItemInDTO foodItem2 = new FoodItemInDTO(1L, "Burger", "Delicious beef burger", 9.99, new byte[]{1, 2, 3});
        FoodItemInDTO foodItem3 = new FoodItemInDTO(2L, "Pizza", "Cheese pizza", 12.99, new byte[]{4, 5, 6});

        assertEquals(foodItem1, foodItem2);
        assertEquals(foodItem1.hashCode(), foodItem2.hashCode());
        assertNotEquals(foodItem1, foodItem3);
        assertNotEquals(foodItem1.hashCode(), foodItem3.hashCode());
    }
}

