package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UpdateCategoryInDTOTest {

    @Test
    void testGettersAndSetters() {
        UpdateCategoryDTO dto = new UpdateCategoryDTO();
        dto.setUserId(1L);
        dto.setName("Test Category");

        assertEquals(1L, dto.getUserId());
        assertEquals("Test Category", dto.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        UpdateCategoryDTO dto1 = new UpdateCategoryDTO(1L, "Test Category");
        UpdateCategoryDTO dto2 = new UpdateCategoryDTO(1L, "Test Category");
        UpdateCategoryDTO dto3 = new UpdateCategoryDTO(2L, "Another Category");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
