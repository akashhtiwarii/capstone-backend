package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.dto.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.CategoryNotFoundException;
import com.capstone.restaurants_service.exceptions.FoodAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.FoodItemNotFoundException;
import com.capstone.restaurants_service.exceptions.InvalidCategoryException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FoodItemServiceImpl for implementing methods of FoodItemService.
 */
@Service
public class FoodItemServiceImpl implements FoodItemService {

    /**
     * FoodItemRepository object.
     */
    @Autowired
    private FoodItemRepository foodItemRepository;
    /**
     * CategoryRepository object.
     */
    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * RestaurantRepository object.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;
    /**
     * User Service communication object.
     */
    @Autowired
    private UserClient userClient;
    /**
     * Add new Food Item.
     * @param foodItemInDTO
     * @param image
     * @return String message
     */
    public String addFoodItem(FoodItemInDTO foodItemInDTO, MultipartFile image) {
        UserOutDTO user = userClient.getUserById(foodItemInDTO.getLoggedInOwnerId()).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_ADD_FOOD);
        }
        String foodItemName = StringUtils.capitalizeFirstLetter(foodItemInDTO.getName());
        Category category = categoryRepository.findById(foodItemInDTO.getCategoryId());
        if (category == null) {
            throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
        }
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant == null) {
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
        if (restaurant.getOwnerId() != user.getUserId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_ADD_FOOD);
        }
        FoodItem foodItemAlreadyExists = foodItemRepository.findByCategoryIdAndName(
                foodItemInDTO.getCategoryId(), foodItemName);
        if (foodItemAlreadyExists != null) {
           throw new FoodAlreadyExistsException(Constants.FOOD_ALREADY_PRESENT);
        }
        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);
        try {
            if (image != null && !image.isEmpty()) {
                foodItem.setImage(image.getBytes());
            }
            foodItemRepository.save(foodItem);
            return Constants.FOOD_ADDED_SUCCESSFULLY;
        } catch (IOException e) {
            throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Delete Food Item.
     * @param userId
     * @param foodId
     * @return String message
     */
    @Override
    public String deleteFoodItem(long userId, long foodId) {
        UserOutDTO user = userClient.getUserById(userId).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_DELETE_FOOD);
        }
        FoodItem foodItem = foodItemRepository.findById(foodId);
        if (foodItem == null) {
            throw new FoodItemNotFoundException(Constants.FOOD_NOT_FOUND);
        }
        Category category = categoryRepository.findById(foodItem.getCategoryId());
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant.getOwnerId() != user.getUserId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_DELETE_FOOD);
        }
        try {
            foodItemRepository.deleteById(foodId);
            return Constants.FOOD_DELETED_SUCCESSFULLY;
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Update Food Item.
     * @param foodItemId
     * @param updateFoodItemInDTO
     * @return String message
     */
    @Override
    public String updateFoodItem(long foodItemId, UpdateFoodItemInDTO updateFoodItemInDTO) {
        UserOutDTO user = userClient.getUserById(updateFoodItemInDTO.getLoggedInOwnerId()).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_FOOD);
        }
        FoodItem foodItem = foodItemRepository.findById(foodItemId);
        if (foodItem == null) {
            throw new FoodItemNotFoundException(Constants.FOOD_NOT_FOUND);
        }
        Category category = categoryRepository.findById(foodItem.getCategoryId());
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant.getOwnerId() != user.getUserId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_FOOD);
        }
        Category foodItemCategory = categoryRepository.findById(updateFoodItemInDTO.getCategoryId());
        Restaurant foodItemRestaurant = restaurantRepository.findById(foodItemCategory.getRestaurantId());
        if (foodItemRestaurant != restaurant) {
            throw new InvalidCategoryException(Constants.INVALID_CATEGORY);
        }
        foodItem.setCategoryId(updateFoodItemInDTO.getCategoryId());
        foodItem.setName(updateFoodItemInDTO.getName());
        foodItem.setDescription(updateFoodItemInDTO.getDescription());
        foodItem.setPrice(updateFoodItemInDTO.getPrice());
        try {
            MultipartFile image = updateFoodItemInDTO.getImage();
            if (image != null && !image.isEmpty()) {
                foodItem.setImage(image.getBytes());
            }
            foodItemRepository.save(foodItem);
            return Constants.FOOD_UPDATED_SUCCESSFULLY;
        } catch (IOException e) {
            throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }
    }

    /**
     * Get Food Items Of Restaurant.
     * @param restaurantId
     * @return Food Items
     */
    @Override
    public List<FoodItem> getAllFoodItemsOfRestaurant(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
        }
        List<FoodItem> allFoodItems = new ArrayList<>();
        for (Category category : categories) {
            List<FoodItem> foodItems = foodItemRepository.findByCategoryId(category.getCategoryId());
            if (!foodItems.isEmpty()) {
                allFoodItems.addAll(foodItems);
            }
        }
        if (allFoodItems.isEmpty()) {
            throw new FoodItemNotFoundException(Constants.FOOD_NOT_FOUND);
        }
        return allFoodItems;
    }

    /**
     * Get Food Items By Category.
     * @param categoryId
     * @return Food Items
     */
    @Override
    public List<FoodItem> getFoodItemsByCategory(long categoryId) {
        try {
            List<FoodItem> foodItems = foodItemRepository.findByCategoryId(categoryId);
            if (foodItems == null) {
               throw new FoodItemNotFoundException(Constants.FOOD_NOT_FOUND);
            }
            return foodItems;
        } catch (Exception ex) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + ex.getMessage());
        }

    }
}
