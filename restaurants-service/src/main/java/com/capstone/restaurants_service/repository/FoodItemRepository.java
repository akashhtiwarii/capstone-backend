package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    /**
     * Get all food items.
     * @return all food items
     */
    List<FoodItem> findAll();
    /**
     * Get all food items by categoryId.
     * @param categoryId for applying filter.
     * @return all food items with a specific categoryId
     */
    List<FoodItem> findByCategoryId(long categoryId);
    /**
     * Get food item by foodId.
     * @param foodId for applying filter.
     * @return food item with a specific foodId
     */
    FoodItem findById(long foodId);
    /**
     * Get food item by foodId.
     * @param name for applying filter.
     * @return food item with a specific name
     */
    FoodItem findByName(String name);
}
