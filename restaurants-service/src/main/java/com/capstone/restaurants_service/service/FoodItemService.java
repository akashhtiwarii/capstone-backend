package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing {@link FoodItem} entities.
 * <p>
 * This interface defines methods for performing CRUD operations and other food item-related
 * actions such as adding, updating, retrieving, and deleting food items in the system.
 * </p>
 */
public interface FoodItemService {

    /**
     * Adds a new food item to the system.
     * <p>
     * This method accepts a {@link FoodItemInDTO} object containing the details of the food item
     * to be added, along with an optional image file. It returns a confirmation message upon
     * successful addition.
     * </p>
     * @param foodItemInDTO the data transfer object containing the details of the food item to be added
     * @param image an optional image file associated with the food item
     * @return a confirmation message indicating the result of the addition operation
     */
    String addFoodItem(FoodItemInDTO foodItemInDTO, MultipartFile image);

    /**
     * Deletes a food item from the system.
     * <p>
     * This method deletes the food item identified by the provided food ID. It requires the ID
     * of the user performing the delete operation.
     * </p>
     * @param userId the ID of the user performing the delete operation
     * @param foodId the ID of the food item to be deleted
     * @return a confirmation message indicating the result of the delete operation
     */
    String deleteFoodItem(long userId, long foodId);

    /**
     * Updates an existing food item.
     * <p>
     * This method updates the details of an existing food item identified by the provided food ID
     * using the information in the {@link UpdateFoodItemInDTO} object. An optional image file
     * can also be associated with the updated food item.
     * </p>
     * @param foodItemId the ID of the food item to be updated
     * @param updateFoodItemInDTO the data transfer object containing the updated details of the food item
     * @param image an optional image file associated with the updated food item
     * @return a confirmation message indicating the result of the update operation
     */
    String updateFoodItem(long foodItemId, UpdateFoodItemInDTO updateFoodItemInDTO, MultipartFile image);

    /**
     * Retrieves all food items associated with a specific restaurant.
     * <p>
     * This method returns a list of {@link FoodItem} entities that belong to the restaurant identified
     * by the provided restaurant ID.
     * </p>
     * @param restaurantId the ID of the restaurant for which food items are to be retrieved
     * @return a {@link List} of {@link FoodItem} entities associated with the given restaurant ID
     */
    List<FoodItem> getAllFoodItemsOfRestaurant(long restaurantId);

    /**
     * Retrieves all food items associated with a specific category.
     * <p>
     * This method returns a list of {@link FoodItem} entities that belong to the category identified
     * by the provided category ID.
     * </p>
     * @param categoryId the ID of the category for which food items are to be retrieved
     * @return a {@link List} of {@link FoodItem} entities associated with the given category ID
     */
    List<FoodItem> getFoodItemsByCategory(long categoryId);

    /**
     * Retrieves a food item by its ID.
     * <p>
     * This method returns a {@link FoodItem} entity identified by the provided food ID.
     * </p>
     * @param foodId the ID of the food item to be retrieved
     * @return the {@link FoodItem} entity associated with the given food ID
     */
    FoodItem getByFoodId(long foodId);
}
