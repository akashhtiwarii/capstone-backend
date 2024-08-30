package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.GetAllCategoriesInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GetAllCategoriesInDTOTest {

    @Test
    void testGettersAndSetters() {
        GetAllCategoriesInDTO dto = new GetAllCategoriesInDTO();
        dto.setRestaurantId(1L);
        dto.setUserId(2L);

        assertEquals(1L, dto.getRestaurantId());
        assertEquals(2L, dto.getUserId());
    }

    @Test
    void testEqualsAndHashCode() {
        GetAllCategoriesInDTO dto1 = new GetAllCategoriesInDTO(1L, 2L);
        GetAllCategoriesInDTO dto2 = new GetAllCategoriesInDTO(1L, 2L);
        GetAllCategoriesInDTO dto3 = new GetAllCategoriesInDTO(3L, 4L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}

