package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CategoryRepository to connect with categories table.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Get the list of all categories for a restaurant.
     * @param restaurantId ID of restaurant
     * @return List of categories
     */
    List<Category> findByRestaurantId(long restaurantId);

    /**
     * Get the category by name.
     * @param name
     * @return Category if present
     */
    Category findByName(String name);

    /**
     * Get Category By Id.
     * @param categoryId
     * @return Category
     */
    Category findById(long categoryId);
}
