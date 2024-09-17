package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.entity.Category;

/**
 * A utility class for converting between {@link CategoryInDTO} and {@link Category} entities.
 * This class provides methods to transform data between DTOs and entity objects.
 */
public final class CategoryConverters {
    /**
     * Private constructor for CategoryConverters.
     */
    private CategoryConverters() {
        throw new UnsupportedOperationException("Utility Class Cannot be instantiated");
    }

    /**
     * Converts a {@link CategoryInDTO} object to a {@link Category} entity.
     *
     * @param categoryInDTO the DTO containing category data to be converted
     * @return a {@link Category} entity populated with data from the provided DTO
     */
    public static Category categoryInDTOToCategoryEntity(final CategoryInDTO categoryInDTO) {
        Category category = new Category();
        category.setRestaurantId(categoryInDTO.getRestaurantId());
        category.setName(categoryInDTO.getName().trim().toUpperCase());
        return category;
    }
}
