package com.capstone.restaurants_service.convertersTest;

import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryConvertersTest {

    @Test
    public void testCategoryInDTOToCategoryEntity() {
        CategoryInDTO categoryInDTO = new CategoryInDTO();
        categoryInDTO.setRestaurantId(1);
        categoryInDTO.setName("testCategory");

        Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);

        assertNotNull(category);
        assertEquals(1, category.getRestaurantId());
        assertEquals("TESTCATEGORY", category.getName());
    }
}