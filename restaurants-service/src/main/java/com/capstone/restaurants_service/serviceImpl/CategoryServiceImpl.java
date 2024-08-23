package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    /**
     * categoryRepository for executing jpa functions with category table.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Get Category with a specific Id.
     * @param categoryId for filtering out
     * @return category with a specific Id
     */
    @Override
    public Category findById(long categoryId) {
        return categoryRepository.findById(categoryId);
    }
    /**
     * Get Category with a specific name.
     * @param name for filtering out
     * @return category with a specific name
     */
    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Save a Category in the database.
     * @param category for saving
     * @return a string with a message
     */
    @Override
    public ResponseEntity<String> save(Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok("New category added successfully");
    }
}
