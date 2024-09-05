package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * FoodItemService for defining methods related to food_items table.
 */
public interface FoodItemService {
    /**
     * Add new Food Item.
     * @param foodItemInDTO
     * @param image
     * @return message string
     */
    String addFoodItem(FoodItemInDTO foodItemInDTO, MultipartFile image);

    /**
     * Delete Food Item.
     * @param userId
     * @param foodId
     * @return String message
     */
    String deleteFoodItem(long userId, long foodId);

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
