package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryConvertersTest {

    @Test
    void testCategoryInDTOToCategoryEntity() {
        CategoryInDTO categoryInDTO = new CategoryInDTO();
        categoryInDTO.setRestaurantId(1L);
        categoryInDTO.setName("test category");

        Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);

        assertEquals(1L, category.getRestaurantId());
        assertEquals("TEST CATEGORY", category.getName());
    }
}
