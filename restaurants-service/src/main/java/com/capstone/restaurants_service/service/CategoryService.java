package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.InDTO.DeleteCategoryInDTO;
import com.capstone.restaurants_service.InDTO.GetAllCategoriesInDTO;
import com.capstone.restaurants_service.InDTO.UpdateCategoryDTO;
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
     * @param getAllCategoriesInDTO
     * @return list of all categories
     */
    List<Category> getAllCategoriesOfRestaurant(GetAllCategoriesInDTO getAllCategoriesInDTO);
    /**
     * Update Category.
     * @param categoryId
     * @param updateCategoryDTO
     * @return updated category
     */
    String updateCategory(long categoryId, UpdateCategoryDTO updateCategoryDTO);
    /**
     * Delete category.
     * @param deleteCategoryInDTO
     * @return String message
     */
    String deleteCategory(DeleteCategoryInDTO deleteCategoryInDTO);
}
