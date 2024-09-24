package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.FoodItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodItemTest {

    @Test
    public void testGetterAndSetter() {
        FoodItem foodItem = new FoodItem();

        assertEquals(0, foodItem.getFoodId());
        foodItem.setFoodId(1);
        assertEquals(1, foodItem.getFoodId());

        assertEquals(0, foodItem.getCategoryId());
        foodItem.setCategoryId(2);
        assertEquals(2, foodItem.getCategoryId());

        assertNull(foodItem.getName());
        foodItem.setName("Food Item Name");
        assertEquals("Food Item Name", foodItem.getName());

        assertNull(foodItem.getDescription());
        foodItem.setDescription("Food Item Description");
        assertEquals("Food Item Description", foodItem.getDescription());

        assertEquals(0.0, foodItem.getPrice());
        foodItem.setPrice(12.34);
        assertEquals(12.34, foodItem.getPrice());

        assertNull(foodItem.getImage());
        byte[] image = {1, 2, 3};
        foodItem.setImage(image);
        assertArrayEquals(image, foodItem.getImage());
    }

    @Test
    public void testToString() {
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodId(1);
        foodItem.setCategoryId(2);
        foodItem.setName("Food Item Name");
        foodItem.setDescription("Food Item Description");
        foodItem.setPrice(12.34);
        foodItem.setImage(new byte[]{1, 2, 3});

        assertEquals("FoodItem(foodId=1, categoryId=2, name=Food Item Name, description=Food Item Description, " +
                "price=12.34, image=[1, 2, 3])", foodItem.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        FoodItem foodItem1 = buildFoodItem(1, 2, "Food Item Name", "Food Item Description", 12.34, new byte[]{1, 2, 3});

        assertEquals(foodItem1, foodItem1);
        assertEquals(foodItem1.hashCode(), foodItem1.hashCode());

        assertNotEquals(foodItem1, new Object());

        FoodItem foodItem2 = buildFoodItem(1, 2, "Food Item Name", "Food Item Description", 12.34, new byte[]{1, 2, 3});
        assertEquals(foodItem1, foodItem2);
        assertEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(2, 2, "Food Item Name", "Food Item Description", 12.34, new byte[]{1, 2, 3});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(1, 3, "Food Item Name", "Food Item Description", 12.34, new byte[]{1, 2, 3});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(1, 2, "Different Name", "Food Item Description", 12.34, new byte[]{1, 2, 3});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(1, 2, "Food Item Name", "Different Description", 12.34, new byte[]{1, 2, 3});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(1, 2, "Food Item Name", "Food Item Description", 56.78, new byte[]{1, 2, 3});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        foodItem2 = buildFoodItem(1, 2, "Food Item Name", "Food Item Description", 12.34, new byte[]{4, 5, 6});
        assertNotEquals(foodItem1, foodItem2);
        assertNotEquals(foodItem1.hashCode(), foodItem2.hashCode());

        FoodItem foodItem3 = new FoodItem();
        FoodItem foodItem4 = new FoodItem();
        assertEquals(foodItem3, foodItem4);
        assertEquals(foodItem3.hashCode(), foodItem4.hashCode());
    }

    private FoodItem buildFoodItem(long foodId, long categoryId, String name, String description, double price, byte[] image) {
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodId(foodId);
        foodItem.setCategoryId(categoryId);
        foodItem.setName(name);
        foodItem.setDescription(description);
        foodItem.setPrice(price);
        foodItem.setImage(image);

        return foodItem;
    }
}