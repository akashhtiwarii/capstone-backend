package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
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
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_ADD_A_CATEGORY);
        }
        String categoryName = categoryInDTO.getName().toUpperCase();
        Restaurant restaurant = restaurantRepository.findById(categoryInDTO.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        if (restaurant.getOwnerId() != categoryInDTO.getUserId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_ADD_A_CATEGORY);
        }
        Category categoryAlreadyExists = categoryRepository.findByNameAndRestaurantId(
                categoryName,
                restaurant.getRestaurantId()
        );
        if (categoryAlreadyExists != null) {
            throw new CategoryAlreadyExistException(Constants.CATEGORY_ALREADY_PRESENT);
        }
        Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);
        try {
            categoryRepository.save(category);
            return Constants.CATEGORY_ADDED_SUCCESSFULLY;
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Get all the categories.
     * @return the list of all categories
     */
    @Override
    public List<Category> getAllCategoriesOfRestaurant(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantsNotFoundException(Constants.NO_RESTAURANTS);
        }
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        if (categories.isEmpty()) {
           throw new CategoryNotFoundException(Constants.NO_CATEGORIES_FOR_RESTAURANT);
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
        String categoryToBeAdded = updateCategoryDTO.getName().toUpperCase();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_CATEGORY);
        }
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
        }
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant.getOwnerId() != updateCategoryDTO.getUserId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_CATEGORY);
        }
        if (!categoryToBeAdded.equals(category.getName())) {
            Category categoryAlreadyExists = categoryRepository.findByNameAndRestaurantId(
                    categoryToBeAdded,
                    restaurant.getRestaurantId()
            );
            if (categoryAlreadyExists != null) {
                throw new CategoryAlreadyExistException(Constants.CATEGORY_ALREADY_PRESENT);
            }
        }
        try {
            category.setName(updateCategoryDTO.getName().toUpperCase());
            categoryRepository.save(category);
            return Constants.CATEGORY_UPDATED_SUCCESSFULLY;
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Delete Category.
     * @param userId
     * @param categoryId
     * @return Message
     */
    @Override
    public String deleteCategory(long userId, long categoryId) {
        UserOutDTO user = userClient.getUserById(userId).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_DELETE_CATEGORY);
        }
        Category category = categoryRepository.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
        }
        if (restaurantRepository.findById(category.getRestaurantId()).getOwnerId() != userId) {
           throw new UserNotValidException(Constants.YOU_CANNOT_DELETE_CATEGORY);
        }
        try {
            categoryRepository.deleteById(categoryId);
            return Constants.CATEGORY_DELETED;
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }

    }
}
