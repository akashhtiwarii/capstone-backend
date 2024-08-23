package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.entity.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    /**
     * Get category with a specific Id.
     * @param categoryId for filtering out
     * @return Category with a specific Id.
     */
    Category findById(long categoryId);
    /**
     * Get category with a specific name.
     * @param name for filtering out
     * @return Category with a specific name.
     */
    Category findByName(String name);
    /**
     * Save a category to database.
     * @param category for saving
     * @return string with a message.
     */
    ResponseEntity<String> save(Category category);
}
