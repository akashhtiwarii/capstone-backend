package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.CategoryInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryInDTOTest {

    @Test
    void testGettersAndSetters() {
        CategoryInDTO categoryInDTO = new CategoryInDTO();
        categoryInDTO.setUserId(1L);
        categoryInDTO.setRestaurantId(2L);
        categoryInDTO.setName("Test Category");

        assertEquals(1L, categoryInDTO.getUserId());
        assertEquals(2L, categoryInDTO.getRestaurantId());
        assertEquals("Test Category", categoryInDTO.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        CategoryInDTO category1 = new CategoryInDTO(1L, 2L, "Test Category");
        CategoryInDTO category2 = new CategoryInDTO(1L, 2L, "Test Category");
        CategoryInDTO category3 = new CategoryInDTO(2L, 3L, "Different Category");

        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1, category3);
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }

    @Test
    public void testToString() {
        CategoryInDTO dto = new CategoryInDTO();
        dto.setUserId(1L);
        dto.setRestaurantId(2L);
        dto.setName("Beverages");

        String expectedString = "CategoryInDTO(userId=1, restaurantId=2, name=Beverages)";

        assertEquals(expectedString, dto.toString());
    }
}

