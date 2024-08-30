package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeleteFoodItemInDTOTest {

    @Test
    void testGettersAndSetters() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO();
        deleteFoodItemInDTO.setUserId(1L);
        deleteFoodItemInDTO.setFoodId(2L);

        assertEquals(1L, deleteFoodItemInDTO.getUserId());
        assertEquals(2L, deleteFoodItemInDTO.getFoodId());
    }

    @Test
    void testEqualsAndHashCode() {
        DeleteFoodItemInDTO deleteFoodItem1 = new DeleteFoodItemInDTO(1L, 2L);
        DeleteFoodItemInDTO deleteFoodItem2 = new DeleteFoodItemInDTO(1L, 2L);
        DeleteFoodItemInDTO deleteFoodItem3 = new DeleteFoodItemInDTO(2L, 3L);

        assertEquals(deleteFoodItem1, deleteFoodItem2);
        assertEquals(deleteFoodItem1.hashCode(), deleteFoodItem2.hashCode());
        assertNotEquals(deleteFoodItem1, deleteFoodItem3);
        assertNotEquals(deleteFoodItem1.hashCode(), deleteFoodItem3.hashCode());
    }
}
