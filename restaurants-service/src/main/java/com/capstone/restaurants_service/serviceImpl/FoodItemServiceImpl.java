package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
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
     * @return String message
     */
    public String addFoodItem(FoodItemInDTO foodItemInDTO) {
        String foodItemName = StringUtils.capitalizeFirstLetter(foodItemInDTO.getName());
        boolean category = categoryRepository.existsById(foodItemInDTO.getCategoryId());
        if (!category) {
            throw new CategoryNotFoundException("Category Does Not Exist");
        }
        FoodItem foodItemAlreadyExists = foodItemRepository.findByCategoryIdAndName(
                foodItemInDTO.getCategoryId(), foodItemName);
        if (foodItemAlreadyExists != null) {
           throw new FoodAlreadyExistsException("Food item already in the menu");
        }
        FoodItem foodItem = FoodItemConverters.foodItemInDTOToFoodItemEntity(foodItemInDTO);
        try {
            foodItemRepository.save(foodItem);
            return "Food Item Added Successfully";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
        }
    }

    /**
     * Delete Food Item.
     * @param deleteFoodItemInDTO
     * @return String message
     */
    @Override
    public String deleteFoodItem(DeleteFoodItemInDTO deleteFoodItemInDTO) {
        UserOutDTO user = userClient.getUserById(deleteFoodItemInDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot delete a food item");
        }
        FoodItem foodItem = foodItemRepository.findById(deleteFoodItemInDTO.getFoodId());
        if (foodItem == null) {
            throw new FoodItemNotFoundException("No Food Item Exists");
        }
        Category category = categoryRepository.findById(foodItem.getCategoryId());
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant.getOwnerId() != user.getUserId()) {
            throw new UserNotValidException("You cannot delete this food item");
        }
        try {
            foodItemRepository.deleteById(deleteFoodItemInDTO.getFoodId());
            return "Deleted Food Item";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
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
        UserOutDTO user = userClient.getUserById(updateFoodItemInDTO.getUserId()).getBody();
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if (user.getRole() != Role.OWNER) {
            throw new UserNotValidException("You cannot update a food item");
        }
        FoodItem foodItem = foodItemRepository.findById(foodItemId);
        if (foodItem == null) {
            throw new FoodItemNotFoundException("No Food Item Exists");
        }
        Category category = categoryRepository.findById(foodItem.getCategoryId());
        Restaurant restaurant = restaurantRepository.findById(category.getRestaurantId());
        if (restaurant.getOwnerId() != user.getUserId()) {
            throw new UserNotValidException("You cannot update a food item");
        }
        Category foodItemCategory = categoryRepository.findById(updateFoodItemInDTO.getCategoryId());
        Restaurant foodItemRestaurant = restaurantRepository.findById(foodItemCategory.getRestaurantId());
        if (foodItemRestaurant != restaurant) {
            throw new InvalidCategoryException("Invalid Category For the Given Food");
        }
        foodItem.setCategoryId(updateFoodItemInDTO.getCategoryId());
        foodItem.setName(updateFoodItemInDTO.getName());
        foodItem.setDescription(updateFoodItemInDTO.getDescription());
        foodItem.setPrice(updateFoodItemInDTO.getPrice());
        try {
            foodItemRepository.save(foodItem);
            return "Food Item Updated Successfully";
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
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
            throw new RestaurantsNotFoundException("No Restaurant with this Id");
        }
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No Category added for this restaurant");
        }
        List<FoodItem> allFoodItems = new ArrayList<>();
        for (Category category : categories) {
            List<FoodItem> foodItems = foodItemRepository.findByCategoryId(category.getCategoryId());
            if (!foodItems.isEmpty()) {
                allFoodItems.addAll(foodItems);
            }
        }
        if (allFoodItems.isEmpty()) {
            throw new FoodItemNotFoundException("No Food Items in the menu");
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
               throw new FoodItemNotFoundException("No Food Items in this category");
            }
            return foodItems;
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage());
        }

    }
}
