package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.entity.Category;

import java.util.List;

/**
 * Service interface for managing {@link Category} entities.
 * <p>
 * This interface defines methods for performing
 * CRUD operations and other category-related
 * actions such as adding, updating, retrieving, and deleting categories in the system.
 * </p>
 */
public interface CategoryService {

    /**
     * Adds a new category to the system.
     * <p>
     * This method accepts a {@link CategoryInDTO} object containing the details of the category
     * to be added and returns a confirmation message upon successful addition.
     * </p>
     * @param categoryInDTO the data transfer object containing the details of the category to be added
     * @return a confirmation message indicating the result of the addition operation
     */
    String addCategory(CategoryInDTO categoryInDTO);

    /**
     * Retrieves all categories associated with a specific restaurant.
     * <p>
     * This method returns a list of {@link Category} entities that belong to the restaurant identified
     * by the provided restaurant ID.
     * </p>
     * @param restaurantId the ID of the restaurant for which categories are to be retrieved
     * @return a {@link List} of {@link Category} entities associated with the given restaurant ID
     */
    List<Category> getAllCategoriesOfRestaurant(long restaurantId);

    /**
     * Updates an existing category.
     * <p>
     * This method updates the details of an existing category identified by the provided category ID
     * using the information in the {@link UpdateCategoryDTO} object.
     * </p>
     * @param categoryId the ID of the category to be updated
     * @param updateCategoryDTO the data transfer object containing the updated details of the category
     * @return a confirmation message indicating the result of the update operation
     */
    String updateCategory(long categoryId, UpdateCategoryDTO updateCategoryDTO);

    /**
     * Deletes a category from the system.
     * <p>
     * This method deletes the category identified by the provided category ID. It requires the ID
     * of the user performing the delete operation.
     * </p>
     * @param userId the ID of the user performing the delete operation
     * @param categoryId the ID of the category to be deleted
     * @return a confirmation message indicating the result of the delete operation
     */
    String deleteCategory(long userId, long categoryId);
}
