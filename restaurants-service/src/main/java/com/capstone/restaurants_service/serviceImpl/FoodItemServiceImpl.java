package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    /**
     * foodItemRepository to connect with food table.
     */
    @Autowired
    private FoodItemRepository foodItemRepository;
    /**
     * Get all food items.
     * @return all food items.
     */
    @Override
    public List<FoodItem> findAll() {
        return foodItemRepository.findAll();
    }

    /**
     * Get all food items of a specific category.
     * @param categoryId for filtering
     * @return food items with a specific category
     */
    @Override
    public List<FoodItem> findByCategoryId(long categoryId) {
        return foodItemRepository.findByCategoryId(categoryId);
    }
    /**
     * Get food item of a specific foodId.
     * @param foodId for filtering
     * @return food item with a specific foodId
     */
    @Override
    public FoodItem findById(long foodId) {
        return foodItemRepository.findById(foodId);
    }
    /**
     * Get food item of a specific name.
     * @param name for filtering
     * @return food item with a specific name
     */
    @Override
    public FoodItem findByName(String name) {
        return foodItemRepository.findByName(name);
    }
    /**
     * Save a food item to database.
     * @param foodItem for saving
     * @return a string with a message
     */
    @Override
    public ResponseEntity<String> save(FoodItem foodItem) {
        foodItemRepository.save(foodItem);
        return ResponseEntity.ok("New food item added successfully");
    }
}
