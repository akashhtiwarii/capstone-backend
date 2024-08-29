package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FoodItemRepository to connect with food_items table.
 */
@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
