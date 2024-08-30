package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UpdateFoodItemInDTOTest {

    @Test
    void testGettersAndSetters() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO();
        dto.setUserId(1L);
        dto.setCategoryId(2L);
        dto.setName("Pizza");
        dto.setDescription("Delicious cheese pizza");
        dto.setPrice(9.99);

        assertEquals(1L, dto.getUserId());
        assertEquals(2L, dto.getCategoryId());
        assertEquals("Pizza", dto.getName());
        assertEquals("Delicious cheese pizza", dto.getDescription());
        assertEquals(9.99, dto.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        UpdateFoodItemInDTO dto1 = new UpdateFoodItemInDTO(1L, 2L, "Pizza", "Delicious cheese pizza", 9.99);
        UpdateFoodItemInDTO dto2 = new UpdateFoodItemInDTO(1L, 2L, "Pizza", "Delicious cheese pizza", 9.99);
        UpdateFoodItemInDTO dto3 = new UpdateFoodItemInDTO(1L, 3L, "Burger", "Tasty burger", 5.99);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
