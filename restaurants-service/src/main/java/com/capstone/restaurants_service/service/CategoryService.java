package com.capstone.restaurants_service.service;

import dto.InDTO.CategoryInDTO;
import dto.InDTO.UpdateCategoryDTO;
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
     * @param restaurantId
     * @return list of all categories
     */
    List<Category> getAllCategoriesOfRestaurant(long restaurantId);
    /**
     * Update Category.
     * @param categoryId
     * @param updateCategoryDTO
     * @return updated category
     */
    String updateCategory(long categoryId, UpdateCategoryDTO updateCategoryDTO);
    /**
     * Delete category.
     * @param userId
     * @param categoryId
     * @return String message
     */
    String deleteCategory(long userId, long categoryId);
}
