package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FoodItemRepository to connect with food_items table.
 */
@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    /**
     * Get Food Item by category ID and Name.
     * @param categoryId
     * @param name
     * @return food item if exists
     */
    FoodItem findByCategoryIdAndName(long categoryId, String name);

    /**
     * Get Food Item By Id.
     * @param foodItemId
     * @return Food Item
     */
    FoodItem findById(long foodItemId);

    /**
     * Get Food Items by Category.
     * @param categoryId
     * @return food items
     */
    List<FoodItem> findByCategoryId(long categoryId);

    /**
     * Get Food items By Restaurant.
     * @param restaurantId
     * @return Food Items
     */
    @Query("SELECT fi FROM FoodItem fi JOIN fi.categoryId c WHERE c.restaurantId = :restaurantId")
    List<FoodItem> getFoodItemsByResturantId(long restaurantId);
}
