package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;

import java.util.List;

/**
 * FoodItemService for defining methods related to food_items table.
 */
public interface FoodItemService {
    /**
     * Add new Food Item.
     * @param foodItemInDTO
     * @return message string
     */
    String addFoodItem(FoodItemInDTO foodItemInDTO);

    /**
     * Delete Food Item.
     * @param deleteFoodItemInDTO
     * @return String message
     */
    String deleteFoodItem(DeleteFoodItemInDTO deleteFoodItemInDTO);

    /**
     * Update Food Item.
     * @param foodItemId
     * @param updateFoodItemInDTO
     * @return String message
     */
    String updateFoodItem(long foodItemId, UpdateFoodItemInDTO updateFoodItemInDTO);

    /**
     * Get Food Items of a Restaurant.
     * @param restaurantId
     * @return Food items
     */
    List<FoodItem> getAllFoodItemsOfRestaurant(long restaurantId);

    /**
     * Get Food Items of a Category.
     * @param categoryId
     * @return Food items
     */
    List<FoodItem> getFoodItemsByCategory(long categoryId);
}
