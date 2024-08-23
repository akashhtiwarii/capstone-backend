package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodItemService {
    /**
     * Get all food items.
     * @return all food items.
     */
    List<FoodItem> findAll();

    /**
     * Get all food items of a specific category.
     * @param categoryId for filtering
     * @return food items with a specific category
     */
    List<FoodItem> findByCategoryId(long categoryId);
    /**
     * Get food item of a specific foodId.
     * @param foodId for filtering
     * @return food item with a specific foodId
     */
    FoodItem findById(long foodId);
    /**
     * Get food item of a specific name.
     * @param name for filtering
     * @return food item with a specific name
     */
    FoodItem findByName(String name);

    /**
     * Save a food item to database.
     * @param foodItem for saving
     * @return a string with a message
     */
    ResponseEntity<String> save(FoodItem foodItem);
}
