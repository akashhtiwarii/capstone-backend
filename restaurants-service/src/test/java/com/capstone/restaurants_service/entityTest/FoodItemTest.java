package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.FoodItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodItemTest {

    @Test
    void testGettersAndSetters() {
        FoodItem foodItem = new FoodItem();
        byte[] image = {1, 2, 3};

        foodItem.setFoodId(1L);
        foodItem.setCategoryId(2L);
        foodItem.setName("Pizza");
        foodItem.setDescription("Cheese Pizza");
        foodItem.setPrice(12.99);
        foodItem.setImage(image);

        assertEquals(1L, foodItem.getFoodId());
        assertEquals(2L, foodItem.getCategoryId());
        assertEquals("Pizza", foodItem.getName());
        assertEquals("Cheese Pizza", foodItem.getDescription());
        assertEquals(12.99, foodItem.getPrice());
        assertArrayEquals(image, foodItem.getImage());
    }

    @Test
    void testEqualsMethod() {
        byte[] image1 = {1, 2, 3};
        byte[] image2 = {1, 2, 3};
        byte[] image3 = {4, 5, 6};

        FoodItem foodItem1 = new FoodItem(1L, 2L, "Burger", "Chicken Burger", 9.99, image1);
        FoodItem foodItem2 = new FoodItem(1L, 2L, "Burger", "Chicken Burger", 9.99, image2);
        FoodItem foodItem3 = new FoodItem(2L, 3L, "Salad", "Green Salad", 5.99, image3);

        assertEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1, foodItem3);
    }

    @Test
    void testEqualsMethodWithDifferentObjects() {
        FoodItem foodItem = new FoodItem(1L, 2L, "Pasta", "Italian Pasta", 7.99, new byte[]{1, 2, 3});

        assertNotEquals(null, foodItem);
        assertNotEquals(foodItem, new Object());
    }

    @Test
    void testHashCodeMethod() {
        byte[] image1 = {1, 2, 3};
        byte[] image2 = {1, 2, 3};
        byte[] image3 = {4, 5, 6};

        FoodItem foodItem1 = new FoodItem(1L, 2L, "Noodles", "Veg Noodles", 8.99, image1);
        FoodItem foodItem2 = new FoodItem(1L, 2L, "Noodles", "Veg Noodles", 8.99, image2);
        FoodItem foodItem3 = new FoodItem(3L, 4L, "Sandwich", "Club Sandwich", 6.99, image3);

        assertEquals(foodItem1.hashCode(), foodItem2.hashCode());
        assertNotEquals(foodItem1.hashCode(), foodItem3.hashCode());
    }
}

