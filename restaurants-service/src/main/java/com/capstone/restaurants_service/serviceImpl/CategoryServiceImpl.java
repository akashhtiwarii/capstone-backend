package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.CategoryAlreadyExistException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * CategoryServiceImpl for implementing methods of CategoryService.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * CategoryRepository Object.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Restaurant Repository Object.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Add Category Implementation.
     * @param categoryInDTO request parameter
     * @return String message
     */
    @Override
    public String addCategory(CategoryInDTO categoryInDTO) {
        String categoryName = categoryInDTO.getName().toUpperCase();
        Restaurant restaurant = restaurantRepository.findById(categoryInDTO.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantsNotFoundException("Restaurant does not exists");
        } else {
            Category categoryAlreadyExists = categoryRepository.findByName(categoryName);
            if (categoryAlreadyExists != null) {
                throw new CategoryAlreadyExistException("Category is already present");
            } else {
                Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);
                try {
                    categoryRepository.save(category);
                    return "Category Added Successfully";
                } catch (Exception ex) {
                    throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Get all the categories.
     * @return the list of all categories
     */
    @Override
    public List<Category> getAllCategoriesOfRestaurant() {
        List<Category> categories = categoryRepository.findAll();
        return Collections.emptyList();
    }
}
