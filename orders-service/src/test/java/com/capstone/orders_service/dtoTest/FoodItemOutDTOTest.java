package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.dto.FoodItemOutDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodItemOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();

        assertEquals(0, foodItemOutDTO.getFoodId());
        long foodId = 123L;
        foodItemOutDTO.setFoodId(foodId);
        assertEquals(foodId, foodItemOutDTO.getFoodId());

        assertEquals(0, foodItemOutDTO.getCategoryId());
        long categoryId = 456L;
        foodItemOutDTO.setCategoryId(categoryId);
        assertEquals(categoryId, foodItemOutDTO.getCategoryId());

        assertNull(foodItemOutDTO.getName());
        String name = "Pizza";
        foodItemOutDTO.setName(name);
        assertEquals(name, foodItemOutDTO.getName());

        assertNull(foodItemOutDTO.getDescription());
        String description = "Delicious pizza with extra cheese";
        foodItemOutDTO.setDescription(description);
        assertEquals(description, foodItemOutDTO.getDescription());

        assertEquals(0.0, foodItemOutDTO.getPrice());
        double price = 15.99;
        foodItemOutDTO.setPrice(price);
        assertEquals(price, foodItemOutDTO.getPrice());

        assertNull(foodItemOutDTO.getImage());
        byte[] image = new byte[]{1, 2, 3};
        foodItemOutDTO.setImage(image);
        assertArrayEquals(image, foodItemOutDTO.getImage());
    }

    @Test
    public void testConstructor() {
        long foodId = 123L;
        long categoryId = 456L;
        String name = "Burger";
        String description = "A juicy cheeseburger";
        double price = 9.99;
        byte[] image = new byte[]{4, 5, 6};

        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO(foodId, categoryId, name, description, price, image);

        assertEquals(foodId, foodItemOutDTO.getFoodId());
        assertEquals(categoryId, foodItemOutDTO.getCategoryId());
        assertEquals(name, foodItemOutDTO.getName());
        assertEquals(description, foodItemOutDTO.getDescription());
        assertEquals(price, foodItemOutDTO.getPrice());
        assertArrayEquals(image, foodItemOutDTO.getImage());
    }

    @Test
    public void testImageClone() {
        byte[] image = new byte[]{7, 8, 9};
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setImage(image);

        byte[] returnedImage = foodItemOutDTO.getImage();
        assertArrayEquals(image, returnedImage);

        returnedImage[0] = 10;
        assertNotEquals(image[0], returnedImage[0]);
    }

    @Test
    public void testEqualsAndHashCode() {
        long foodId = 123L;
        long categoryId = 456L;
        String name = "Sushi";
        String description = "Fresh sushi platter";
        double price = 25.50;
        byte[] image = new byte[]{10, 20, 30};

        FoodItemOutDTO foodItemOutDTO1 = new FoodItemOutDTO(foodId, categoryId, name, description, price, image);
        FoodItemOutDTO foodItemOutDTO2 = new FoodItemOutDTO(foodId, categoryId, name, description, price, image);

        assertEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());

        foodItemOutDTO2.setName("Changed Name");
        assertNotEquals(foodItemOutDTO1, foodItemOutDTO2);
        assertNotEquals(foodItemOutDTO1.hashCode(), foodItemOutDTO2.hashCode());
    }

    @Test
    public void testEqualsWithNullAndDifferentObject() {
        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        assertNotEquals(foodItemOutDTO, null);
        assertNotEquals(foodItemOutDTO, new Object());
    }
}
