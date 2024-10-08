package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.ResourceNotFoundException;
import com.capstone.restaurants_service.exceptions.ResourceNotValidException;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.utils.StringUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link FoodItemService} interface for managing {@link FoodItem} entities.
 * <p>
 * This service provides the business logic for creating, updating, retrieving, and deleting food items
 * within the system. It interacts with the {@link FoodItemRepository}, {@link CategoryRepository}, and
 * {@link RestaurantRepository} to perform these operations. It also uses the {@link UserClient} to validate
 * user roles and permissions.
 * </p>
 */
@Service
@Slf4j
public class FoodItemServiceImpl implements FoodItemService {

    /**
     * Repository for performing CRUD operations on {@link FoodItem} entities.
     */
    @Autowired
    private FoodItemRepository foodItemRepository;

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
     * Feign client for communicating with the user service to validate user roles and permissions.
     */
    @Autowired
    private UserClient userClient;

    /**
     * Adds a new food item to the system.
     * <p>
     * This method checks if the user has the appropriate role and verifies the existence of the category
     * and restaurant associated with the food item. It then ensures that the food item does not already
     * exist for the given category. If all checks pass, it saves the food item and returns a confirmation message.
     * </p>
     * @param foodItemInDTO the data transfer object containing the details of the food item to be added
     * @param image the image file associated with the food item
     * @return a confirmation message indicating the result of the addition operation
     * @throws ResourceNotFoundException if the user, category, or restaurant is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot add the food item
     * @throws ResourceAlreadyExistsException if the food item already exists for the given category
     */
    public String addFoodItem(final FoodItemInDTO foodItemInDTO, final MultipartFile image) {
        try {
            UserOutDTO user = userClient.getUserById(foodItemInDTO.getLoggedInOwnerId()).getBody();
            if (user == null) {
                log.error("User not found for ID: {}", foodItemInDTO.getLoggedInOwnerId());
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                log.error("User with ID {} does not have OWNER role", foodItemInDTO.getLoggedInOwnerId());
                throw new ResourceNotValidException(Constants.YOU_CANNOT_ADD_FOOD);
            }
            String foodItemName = StringUtils.capitalizeFirstLetter(foodItemInDTO.getName());
            Category category = categoryRepository.findById(foodItemInDTO.getCategoryId());
            if (category == null) {
                log.error("Category not found for ID: {}", foodItemInDTO.getCategoryId());
                throw new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND);
            }
            Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
            if (restaurant == null) {
                log.error("Restaurant not found for ID: {}", category.getRestaurantId());
                throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
            }
            if (restaurant.getOwnerId() != user.getUserId()) {
                log.error(
                        "Restaurant owner ID {} does not match user ID {}", restaurant.getOwnerId(), user.getUserId()
                );
                throw new ResourceNotValidException(Constants.YOU_CANNOT_ADD_FOOD);
            }
            FoodItem foodItemAlreadyExists = foodItemRepository.findByCategoryIdAndName(
                    foodItemInDTO.getCategoryId(), foodItemName);
            if (foodItemAlreadyExists != null) {
                log.error("Food item {} already exists in category ID {}", foodItemName, foodItemInDTO.getCategoryId());
                throw new ResourceAlreadyExistsException(Constants.FOOD_ALREADY_PRESENT);
            }
            FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);
            try {
                if (image != null && !image.isEmpty()) {
                    String contentType = image.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        log.error("Invalid image type for file: {}", image.getOriginalFilename());
                        throw new ResourceNotValidException(Constants.INVALID_IMAGE_TYPE);
                    }
                    foodItem.setImage(image.getBytes());
                }
                foodItemRepository.save(foodItem);
                return Constants.FOOD_ADDED_SUCCESSFULLY;
            } catch (ResourceNotValidException e) {
                log.error("Validation failed: {}", e.getMessage());
                throw e;
            } catch (IOException e) {
                log.error("Failed to upload image: {}", e.getMessage());
                throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
            } catch (Exception ex) {
                log.error("Unexpected error occurred: {}", ex.getMessage());
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            log.error("User service is down: {}", e.getMessage());
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Deletes an existing food item from the system.
     * <p>
     * This method checks if the user has the appropriate role and verifies the existence of the food item
     * and its associated category and restaurant. It then deletes the food item and returns a confirmation message.
     * </p>
     * @param userId the ID of the user requesting the deletion
     * @param foodId the ID of the food item to be deleted
     * @return a confirmation message indicating the result of the deletion operation
     * @throws ResourceNotFoundException if the user or food item is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot delete the food item
     */
    @Override
    public String deleteFoodItem(final long userId, final long foodId) {
        try {
            UserOutDTO user = userClient.getUserById(userId).getBody();
            if (user == null) {
                log.error("User not found for ID: {}", userId);
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                log.error("User with ID {} does not have OWNER role", userId);
                throw new ResourceNotValidException(Constants.YOU_CANNOT_DELETE_FOOD);
            }
            FoodItem foodItem = foodItemRepository.findById(foodId);
            if (foodItem == null) {
                log.error("Food item not found for ID: {}", foodId);
                throw new ResourceNotFoundException(Constants.FOOD_NOT_FOUND);
            }
            Category category = categoryRepository.findById(foodItem.getCategoryId());
            Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
            if (restaurant.getOwnerId() != user.getUserId()) {
                log.error(
                        "Restaurant owner ID {} does not match user ID {}", restaurant.getOwnerId(), user.getUserId()
                );
                throw new ResourceNotValidException(Constants.YOU_CANNOT_DELETE_FOOD);
            }
            try {
                foodItemRepository.deleteById(foodId);
                return Constants.FOOD_DELETED_SUCCESSFULLY;
            } catch (Exception ex) {
                log.error("Unexpected error occurred: {}", ex.getMessage());
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            log.error("User service is down: {}", e.getMessage());
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Updates an existing food item.
     * <p>
     * This method validates the user's role and checks if the food item exists. It also ensures that
     * the user owns the restaurant associated with the food item and verifies that the new category
     * belongs to the same restaurant. It updates the food item details and returns a confirmation message.
     * </p>
     * @param foodItemId the ID of the food item to be updated
     * @param updateFoodItemInDTO the data transfer object containing the updated details of the food item
     * @param image the new image file associated with the food item
     * @return a confirmation message indicating the result of the update operation
     * @throws ResourceNotFoundException if the user, food item, or category is not found
     * @throws ResourceNotValidException if the user does not have the correct role or cannot update the food item
     */
    @Override
    public String updateFoodItem(
            final long foodItemId, final UpdateFoodItemInDTO updateFoodItemInDTO, final MultipartFile image
    ) {
        try {
            UserOutDTO user = userClient.getUserById(updateFoodItemInDTO.getLoggedInOwnerId()).getBody();
            if (user == null) {
                log.error("User not found for ID: {}", updateFoodItemInDTO.getLoggedInOwnerId());
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() != Role.OWNER) {
                log.error("User with ID {} does not have OWNER role", updateFoodItemInDTO.getLoggedInOwnerId());
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_FOOD);
            }
            FoodItem foodItem = foodItemRepository.findById(foodItemId);
            if (foodItem == null) {
                log.error("Food item not found for ID: {}", foodItemId);
                throw new ResourceNotFoundException(Constants.FOOD_NOT_FOUND);
            }
            Category category = categoryRepository.findById(foodItem.getCategoryId());
            Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
            if (restaurant.getOwnerId() != user.getUserId()) {
                log.error(
                        "Restaurant owner ID {} does not match user ID {}", restaurant.getOwnerId(), user.getUserId()
                );
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_FOOD);
            }
            Category foodItemCategory = categoryRepository.findById(updateFoodItemInDTO.getCategoryId());
            Restaurant foodItemRestaurant = restaurantRepository.findById(foodItemCategory.getRestaurantId());
            if (foodItemRestaurant != restaurant) {
                log.error("Invalid category. Restaurant ID {} does not match food item restaurant ID {}",
                        foodItemRestaurant.getRestaurantId(), restaurant.getRestaurantId());
                throw new ResourceNotValidException(Constants.INVALID_CATEGORY);
            }
            foodItem.setCategoryId(updateFoodItemInDTO.getCategoryId());
            foodItem.setName(StringUtils.capitalizeFirstLetter(
                    updateFoodItemInDTO.getName().trim().replaceAll("\\s+", " "))
            );
            foodItem.setDescription(updateFoodItemInDTO.getDescription().trim().replaceAll("\\s+", " "));
            foodItem.setPrice(updateFoodItemInDTO.getPrice());
            try {
                if (image != null && !image.isEmpty()) {
                    String contentType = image.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        log.error("Invalid image type for file: {}", image.getOriginalFilename());
                        throw new ResourceNotValidException(Constants.INVALID_IMAGE_TYPE);
                    }
                    foodItem.setImage(image.getBytes());
                }
                foodItemRepository.save(foodItem);
                return Constants.FOOD_UPDATED_SUCCESSFULLY;
            } catch (ResourceNotValidException e) {
                log.error("Validation failed: {}", e.getMessage());
                throw e;
            } catch (IOException e) {
                log.error("Failed to upload image: {}", e.getMessage());
                throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
            } catch (Exception ex) {
                log.error("Unexpected error occurred: {}", ex.getMessage());
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
            }
        } catch (FeignException e) {
            log.error("User service is down: {}", e.getMessage());
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }
    /**
     * Retrieves all food items for a specified restaurant.
     * <p>
     * This method fetches the restaurant, checks if it exists, and then retrieves all categories associated
     * with that restaurant. It then collects all food items from those categories and returns them. If no
     * food items are found, it throws an exception.
     * </p>
     * @param restaurantId the ID of the restaurant for which to retrieve food items
     * @return a list of {@link FoodItem} entities associated with the specified restaurant
     * @throws ResourceNotFoundException if the restaurant or categories are not found, or if no food items are found
     */
    @Override
    public List<FoodItem> getAllFoodItemsOfRestaurant(final long restaurantId) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId);
            if (restaurant == null) {
                log.error("Restaurant not found for ID: {}", restaurantId);
                throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
            }
            List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
            if (categories.isEmpty()) {
                log.error("No categories found for restaurant ID: {}", restaurantId);
                throw new ResourceNotFoundException(Constants.CATEGORY_NOT_FOUND);
            }
            List<FoodItem> allFoodItems = new ArrayList<>();
            for (Category category : categories) {
                List<FoodItem> foodItems = foodItemRepository.findByCategoryId(category.getCategoryId());
                if (!foodItems.isEmpty()) {
                    allFoodItems.addAll(foodItems);
                }
            }
            if (allFoodItems.isEmpty()) {
                log.error("No food items found for restaurant ID: {}", restaurantId);
                throw new ResourceNotFoundException(Constants.FOOD_NOT_FOUND);
            }
            return allFoodItems;
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Retrieves all food items for a specified category.
     * <p>
     * This method retrieves the food items associated with the given category ID. If no food items are found,
     * it throws an exception.
     * </p>
     * @param categoryId the ID of the category for which to retrieve food items
     * @return a list of {@link FoodItem} entities associated with the specified category
     * @throws ResourceNotFoundException if no food items are found for the given category
     */
    @Override
    public List<FoodItem> getFoodItemsByCategory(final long categoryId) {
        try {
            List<FoodItem> foodItems = foodItemRepository.findByCategoryId(categoryId);
            if (foodItems == null || foodItems.isEmpty()) {
                log.error("No food items found for category ID: {}", categoryId);
                throw new ResourceNotFoundException(Constants.FOOD_NOT_FOUND);
            }
            return foodItems;
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Retrieves a specific food item by its ID.
     * <p>
     * This method fetches the food item associated with the provided food ID. If the food item is not found,
     * it throws an exception.
     * </p>
     * @param foodId the ID of the food item to retrieve
     * @return the {@link FoodItem} entity with the specified ID
     * @throws ResourceNotFoundException if the food item is not found
     */
    @Override
    public FoodItem getByFoodId(final long foodId) {
        try {
            FoodItem foodItem = foodItemRepository.findById(foodId);
            if (foodItem == null) {
                log.error("Food item not found for ID: {}", foodId);
                throw new ResourceNotFoundException("Food Item Not Found");
            }
            return foodItem;
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }
}

