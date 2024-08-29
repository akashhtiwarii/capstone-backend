package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.entity.Category;

/**
 * Category In DTOs to Entity Converters and Vice Versa.
 */
public class CategoryConverters {

    /**
     * Category In DTO To Category Entity.
     * @param categoryInDTO
     * @return Category entity
     */
    public static Category categoryInDTOToCategoryEntity(CategoryInDTO categoryInDTO) {
        Category category = new Category();
        category.setRestaurantId(categoryInDTO.getRestaurantId());
        category.setName(categoryInDTO.getName().toUpperCase());
        return category;
    }
}
