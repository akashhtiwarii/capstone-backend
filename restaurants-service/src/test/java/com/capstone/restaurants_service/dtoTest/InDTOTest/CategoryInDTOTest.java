package com.capstone.restaurants_service.dtoTest.InDTOTest;

import com.capstone.restaurants_service.dto.InDTO.CategoryInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryInDTOTest {

    @Test
    void testGettersAndSetters() {
        CategoryInDTO categoryInDTO = new CategoryInDTO();
        categoryInDTO.setUserId(1L);
        categoryInDTO.setRestaurantId(2L);
        categoryInDTO.setName("Test Category");
        categoryInDTO.setImage(new byte[]{1, 2, 3, 4, 5});

        assertEquals(1L, categoryInDTO.getUserId());
        assertEquals(2L, categoryInDTO.getRestaurantId());
        assertEquals("Test Category", categoryInDTO.getName());
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5}, categoryInDTO.getImage());
    }

    @Test
    void testEqualsAndHashCode() {
        CategoryInDTO category1 = new CategoryInDTO(1L, 2L, "Test Category", new byte[]{1, 2, 3});
        CategoryInDTO category2 = new CategoryInDTO(1L, 2L, "Test Category", new byte[]{1, 2, 3});
        CategoryInDTO category3 = new CategoryInDTO(2L, 3L, "Different Category", new byte[]{4, 5, 6});

        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1, category3);
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }
}

