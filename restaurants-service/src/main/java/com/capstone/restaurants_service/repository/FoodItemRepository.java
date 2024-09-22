package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link FoodItem} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and custom query methods for the {@link FoodItem} entity. It includes methods for
 * retrieving food items based on various criteria.
 * </p>
 */
@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    /**
     * Retrieves a {@link FoodItem} by its associated category ID and name.
     * <p>
     * This method queries the database to find a food item that matches the provided category ID
     * and name.
     * </p>
     * @param categoryId the ID of the category to which the food item belongs
     * @param name the name of the food item to be retrieved
     * @return the {@link FoodItem} entity that matches the given category ID and name,
     *         or {@code null} if no matching food item is found
     */
    FoodItem findByCategoryIdAndName(long categoryId, String name);

    /**
     * Retrieves a {@link FoodItem} by its ID.
     * <p>
     * This method queries the database to find a food item that matches the provided ID.
     * </p>
     * @param foodItemId the ID of the food item to be retrieved
     * @return the {@link FoodItem} entity that matches the given ID, or {@code null} if no matching food item is found
     */
    FoodItem findById(long foodItemId);

    /**
     * Retrieves a list of {@link FoodItem} entities associated with a specific category.
     * <p>
     * This method queries the database to find all food items that belong to the provided category ID.
     * </p>
     * @param categoryId the ID of the category whose food items are to be retrieved
     * @return a {@link List} of {@link FoodItem} entities associated with the given category ID
     */
    List<FoodItem> findByCategoryId(long categoryId);
}
