package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.ResourceNotFoundException;
import com.capstone.restaurants_service.exceptions.ResourceNotValidException;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.CategoryService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CategoryService} interface for managing {@link Category} entities.
 * <p>
 * This service handles business logic for adding, updating, retrieving, and deleting categories
 * within the system. It interacts with the {@link CategoryRepository} and {@link RestaurantRepository}
 * to perform CRUD operations and uses {@link UserClient} to validate user roles.
 * </p>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * Repository for performing CRUD operations on {@link Category} entities.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Repository for performing CRUD operations on {@link Restaurant} entities.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Feign client for communicating with the user service to validate user roles.
     */
    @Autowired
    private UserClient userClient;

    /**
     * Adds a new category.
     * <p>
     * This method checks if the user has the correct role and validates that the restaurant exists and
     * is owned by the user. It then checks if the category already exists for the restaurant and adds
     * the category if it does not. It returns a confirmation message upon successful addition.
     * </p>
     * @param categoryInDTO the data transfer object containing the details of the category to be added
     * @return a confirmation message indicating the result of the addition operation
     * @throws ResourceNotFoundException if the user or restaurant is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot add the category
     * @throws ResourceAlreadyExistsException if the category already exists for the restaurant
     */
    @Override
    public String addCategory(final CategoryInDTO categoryInDTO) {
        try {
            UserOutDTO user = userClient.getUserById(categoryInDTO.getUserId()).getBody();

            if (user == null) {
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_ADD_A_CATEGORY);
            }
            String categoryName = categoryInDTO.getName().toUpperCase();
            Restaurant restaurant = restaurantRepository.findById(categoryInDTO.getRestaurantId());
            if (restaurant == null) {
                throw new ResourceNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
            }
            if (restaurant.getOwnerId() != categoryInDTO.getUserId()) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_ADD_A_CATEGORY);
            }
            Category categoryAlreadyExists = categoryRepository.findByNameAndRestaurantId(
                    categoryName,
                    restaurant.getRestaurantId()
            );
            if (categoryAlreadyExists != null) {
                throw new ResourceAlreadyExistsException(Constants.CATEGORY_ALREADY_PRESENT);
            }
            Category category = CategoryConverters.categoryInDTOToCategoryEntity(categoryInDTO);
            try {
                categoryRepository.save(category);
                return Constants.CATEGORY_ADDED_SUCCESSFULLY;
            } catch (Exception ex) {
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Retrieves all categories for a specific restaurant.
     * <p>
     * This method checks if the restaurant exists and then retrieves all categories associated with it.
     * It returns a list of categories or throws an exception if no categories are found.
     * </p>
     * @param restaurantId the ID of the restaurant for which categories are to be retrieved
     * @return a list of {@link Category} entities associated with the given restaurant ID
     * @throws ResourceNotFoundException if the restaurant does not exist or if no categories are found
     */
    @Override
    public List<Category> getAllCategoriesOfRestaurant(final long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new ResourceNotFoundException(Constants.NO_RESTAURANTS);
        }
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException(Constants.NO_CATEGORIES_FOR_RESTAURANT);
        }
        return categories;
    }

    /**
     * Updates an existing category.
     * <p>
     * This method validates the user's role and checks if the category exists. It also ensures that
     * the user owns the restaurant associated with the category. If the category name is changed, it
     * checks if the new name already exists for the restaurant. If all validations pass, it updates
     * the category and returns a confirmation message.
     * </p>
     * @param categoryId the ID of the category to be updated
     * @param updateCategoryDTO the data transfer object containing the updated details of the category
     * @return a confirmation message indicating the result of the update operation
     * @throws ResourceNotFoundException if the user or category is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot update the category
     * @throws ResourceAlreadyExistsException if the updated category name already exists for the restaurant
     */
    @Override
    public String updateCategory(final long categoryId, final UpdateCategoryDTO updateCategoryDTO) {
        try {
            UserOutDTO user = userClient.getUserById(updateCategoryDTO.getUserId()).getBody();
            String categoryToBeAdded = updateCategoryDTO.getName().toUpperCase();
            if (user == null) {
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_CATEGORY);
            }
            Category category = categoryRepository.findById(categoryId);
            if (category == null) {
                throw new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND);
            }
            Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
            if (restaurant.getOwnerId() != updateCategoryDTO.getUserId()) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_CATEGORY);
            }
            if (!categoryToBeAdded.equals(category.getName())) {
                Category categoryAlreadyExists = categoryRepository.findByNameAndRestaurantId(
                        categoryToBeAdded,
                        restaurant.getRestaurantId()
                );
                if (categoryAlreadyExists != null) {
                    throw new ResourceAlreadyExistsException(Constants.CATEGORY_ALREADY_PRESENT);
                }
            }
            try {
                category.setName(updateCategoryDTO.getName().trim().toUpperCase());
                categoryRepository.save(category);
                return Constants.CATEGORY_UPDATED_SUCCESSFULLY;
            } catch (Exception ex) {
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Deletes an existing category.
     * <p>
     * This method validates the user's role and ensures the category exists before attempting to delete it.
     * It also verifies that the user owns the restaurant associated with the category. If all checks pass,
     * it deletes the category and returns a confirmation message.
     * </p>
     * @param userId the ID of the user requesting the deletion
     * @param categoryId the ID of the category to be deleted
     * @return a confirmation message indicating the result of the deletion operation
     * @throws ResourceNotFoundException if the user or category is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot delete the category
     */
    @Override
    public String deleteCategory(final long userId, final long categoryId) {
        try {
            UserOutDTO user = userClient.getUserById(userId).getBody();
            if (user == null) {
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_DELETE_CATEGORY);
            }
            Category category = categoryRepository.findById(categoryId);
            if (category == null) {
                throw new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND);
            }
            if (restaurantRepository.findById(category.getRestaurantId()).getOwnerId() != userId) {
                throw new ResourceNotValidException(Constants.YOU_CANNOT_DELETE_CATEGORY);
            }
            try {
                categoryRepository.deleteById(categoryId);
                return Constants.CATEGORY_DELETED;
            } catch (Exception ex) {
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }
}
