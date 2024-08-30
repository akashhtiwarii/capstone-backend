package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.DeleteCategoryInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeleteCategoryInDTOTest {

    @Test
    void testGettersAndSetters() {
        DeleteCategoryInDTO deleteCategoryInDTO = new DeleteCategoryInDTO();
        deleteCategoryInDTO.setUserId(1L);
        deleteCategoryInDTO.setCategoryId(2L);

        assertEquals(1L, deleteCategoryInDTO.getUserId());
        assertEquals(2L, deleteCategoryInDTO.getCategoryId());
    }

    @Test
    void testEqualsAndHashCode() {
        DeleteCategoryInDTO deleteCategory1 = new DeleteCategoryInDTO(1L, 2L);
        DeleteCategoryInDTO deleteCategory2 = new DeleteCategoryInDTO(1L, 2L);
        DeleteCategoryInDTO deleteCategory3 = new DeleteCategoryInDTO(2L, 3L);

        assertEquals(deleteCategory1, deleteCategory2);
        assertEquals(deleteCategory1.hashCode(), deleteCategory2.hashCode());
        assertNotEquals(deleteCategory1, deleteCategory3);
        assertNotEquals(deleteCategory1.hashCode(), deleteCategory3.hashCode());
    }
}

