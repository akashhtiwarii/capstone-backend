package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.entity.Category;

import java.util.List;

/**
 * CategoryService for defining methods related to categories table.
 */
public interface CategoryService {
    /**
     * Add a new Category.
     * @param categoryInDTO request parameter
     * @return String message
     */
    String addCategory(CategoryInDTO categoryInDTO);

    /**
     * Get All the categories.
     * @return list of all categories
     */
    List<Category> getAllCategoriesOfRestaurant();
}
