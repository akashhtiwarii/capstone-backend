package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.InDTO.DeleteCategoryInDTO;
import com.capstone.restaurants_service.InDTO.GetAllCategoriesInDTO;
import com.capstone.restaurants_service.InDTO.UpdateCategoryDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.CategoryAlreadyExistException;
import com.capstone.restaurants_service.exceptions.CategoryNotFoundException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * User Service Communication object.
     */
    @Autowired
    private UserClient userClient;

    /**
     * Add Category Implementation.
     * @param categoryInDTO request parameter
     * @return String message
     */
    @Override
    public String addCategory(CategoryInDTO categoryInDTO) {
        UserOutDTO user = userClient.getUserById(categoryInDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot Add a category");
        }
        String categoryName = categoryInDTO.getName().toUpperCase();
        Restaurant restaurant = restaurantRepository.findById(categoryInDTO.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantsNotFoundException("Restaurant does not exists");
        }
        if (restaurant.getOwnerId() != categoryInDTO.getUserId()) {
            throw new UserNotValidException("You cannot add a category to this restaurant");
        }
        Category categoryAlreadyExists = categoryRepository.findByName(categoryName);
        if (categoryAlreadyExists != null) {
            throw new CategoryAlreadyExistException("Category is already present");
        }
        Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);
        try {
            categoryRepository.save(category);
            return "Category Added Successfully";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
        }
    }

    /**
     * Get all the categories.
     * @return the list of all categories
     */
    @Override
    public List<Category> getAllCategoriesOfRestaurant(GetAllCategoriesInDTO getAllCategoriesInDTO) {
        UserOutDTO user = userClient.getUserById(getAllCategoriesInDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot update a category");
        }
        Restaurant restaurant = restaurantRepository.findById(getAllCategoriesInDTO.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantsNotFoundException("You do not have any restaurants");
        }
        List<Category> categories = categoryRepository.findByRestaurantId(getAllCategoriesInDTO.getRestaurantId());
        if (categories.isEmpty()) {
           throw new CategoryNotFoundException("No Categories for your restaurant");
        }
        return categories;
    }

    /**
     * Update Category.
     * @param categoryId
     * @param updateCategoryDTO
     * @return Message
     */
    @Override
    public String updateCategory(long categoryId, UpdateCategoryDTO updateCategoryDTO) {
        UserOutDTO user = userClient.getUserById(updateCategoryDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot update a category");
        }
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found");
        }
        if (restaurantRepository.findById(category.getRestaurantId()).getOwnerId() != updateCategoryDTO.getUserId()) {
            throw new UserNotValidException("You cannot update this category");
        }
        try {
            category.setName(updateCategoryDTO.getName());
            categoryRepository.save(category);
            return "Category updated successfully";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred " + ex.getMessage());
        }
    }

    /**
     * Delete Category.
     * @param deleteCategoryInDTO
     * @return Message
     */
    @Override
    public String deleteCategory(DeleteCategoryInDTO deleteCategoryInDTO) {
        UserOutDTO user = userClient.getUserById(deleteCategoryInDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot delete a category");
        }
        Category category = categoryRepository.findById(deleteCategoryInDTO.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found");
        }
        if (restaurantRepository.findById(category.getRestaurantId()).getOwnerId() != deleteCategoryInDTO.getUserId()) {
           throw new UserNotValidException("You cannot delete this category");
        }
        try {
            categoryRepository.deleteById(deleteCategoryInDTO.getCategoryId());
            return "Category Deleted Successfully";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred " + ex.getMessage());
        }

    }
}
