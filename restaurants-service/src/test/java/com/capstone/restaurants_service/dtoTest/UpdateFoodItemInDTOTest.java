package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateFoodItemInDTOTest {

    @Test
    public void testEqualsAndHashCode() {
        UpdateFoodItemInDTO dto1 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.99);
        UpdateFoodItemInDTO dto2 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.99);
        UpdateFoodItemInDTO dto3 = new UpdateFoodItemInDTO(2L, 3L, "Another Item", "Description2", 15.99);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto2, dto3);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    public void testEqualsWithNull() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.99);
        assertNotEquals(dto, null);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.99);
        assertNotEquals(dto, new Object());
    }

    @Test
    public void testGettersAndSetters() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO();
        dto.setLoggedInOwnerId(1L);
        dto.setCategoryId(2L);
        dto.setName("Food Item");
        dto.setDescription("Description");
        dto.setPrice(10.99);

        assertEquals(1L, dto.getLoggedInOwnerId());
        assertEquals(2L, dto.getCategoryId());
        assertEquals("Food Item", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals(10.99, dto.getPrice());
    }

    @Test
    public void testEmptyDescription() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "", 10.99);
        assertEquals("", dto.getDescription());
    }

    @Test
    public void testNullDescription() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(1L, 2L, "Food Item", null, 10.99);
        assertNull(dto.getDescription());
    }

    @Test
    public void testToString() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO();
        dto.setLoggedInOwnerId(1L);
        dto.setCategoryId(2L);
        dto.setName("Pizza");
        dto.setDescription("Delicious cheese pizza");
        dto.setPrice(10.99);

        String expectedString = "UpdateFoodItemInDTO(loggedInOwnerId=1, categoryId=2, name=Pizza, description=Delicious cheese pizza, price=10.99)";

        assertEquals(expectedString, dto.toString());
    }

}