package com.capstone.restaurants_service.controller;

import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.RequestSuccessOutDTO;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.service.CategoryService;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.service.RestaurantService;
import com.capstone.restaurants_service.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * REST Controller for managing restaurant-related operations.
 */
@RestController
@RequestMapping(Constants.RESTAURANT_ENDPOINT)
@CrossOrigin
public class RestaurantController {

    /**
     * Service for handling restaurant-related operations.
     */
    @Autowired
    private RestaurantService restaurantService;

    /**
     * Service for handling category-related operations.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Service for handling food item-related operations.
     */
    @Autowired
    private FoodItemService foodItemService;

    /**
     * Logger for logging information and debug messages.
     */
    private final Logger logger = LogManager.getLogger(RestaurantController.class);

    /**
     * Adds a new restaurant.
     * @param restaurantInDTO DTO containing the details of the restaurant to be added.
     * @param image Optional image file for the restaurant.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PostMapping(Constants.ADD_RESTAURANT_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addRestaurant(
            @ModelAttribute @Valid RestaurantInDTO restaurantInDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        logger.info("Adding Restaurant : {}", restaurantInDTO.getName());
        logger.info("Restaurant Details Fetched");
        logger.info("Image Fetched");
        String message = restaurantService.save(restaurantInDTO, image);
        logger.info("Restaurant Added");
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Retrieves a restaurant by its ID.
     * @param restaurantId The ID of the restaurant to retrieve.
     * @return ResponseEntity containing the restaurant details.
     */
    @GetMapping(Constants.GET_RESTAURANT_BY_ID)
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Restaurant by Id : {}", restaurantId);
        Restaurant restaurant = restaurantService.findById(restaurantId);
        logger.info("Fetched Restaurant by Id : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    /**
     * Retrieves all restaurants owned by a specific owner.
     * @param ownerId The ID of the owner whose restaurants are to be fetched.
     * @return ResponseEntity containing a list of restaurants owned by the specified owner.
     */
    @GetMapping(Constants.GET_OWNER_RESTAURANTS)
    public ResponseEntity<List<Restaurant>> getOwnerRestaurants(
            @RequestParam @Min(value = 1, message = "Valid Owner ID required") long ownerId) {
        logger.info("Fetching Restaurants of ownerID : {}", ownerId);
        List<Restaurant> restaurants = restaurantService.findByOwnerId(ownerId);
        logger.info("Fetched Restaurants of ownerId : {}", ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    /**
     * Retrieves all restaurants.
     * @return ResponseEntity containing a list of all restaurants.
     */
    @GetMapping(Constants.GET_RESTAURANTS)
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        logger.info("Fetching Restaurants");
        List<Restaurant> restaurants = restaurantService.findAll();
        logger.info("Fetched Restaurants");
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    /**
     * Adds a new category to a restaurant.
     * @param categoryInDTO DTO containing the details of the category to be added.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PostMapping(Constants.ADD_CATEGORIES_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addCategory(@Valid @RequestBody CategoryInDTO categoryInDTO) {
        logger.info("Adding Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        String response = categoryService.addCategory(categoryInDTO);
        logger.info("Added Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Updates the details of an existing restaurant.
     * @param updateRestaurantInDTO DTO containing the updated details of the restaurant.
     * @param image Optional image file for the restaurant.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PutMapping(value = Constants.UPDATE_RESTAURANT_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> updateRestaurant(
            @ModelAttribute @Valid UpdateRestaurantInDTO updateRestaurantInDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        logger.info("Updating Restaurant for restaurant ID: {}", updateRestaurantInDTO.getRestaurantId());
        String response = restaurantService.updateRestaurant(updateRestaurantInDTO, image);
        logger.info("Updated Restaurant for restaurant ID: {}", updateRestaurantInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves all categories of a restaurant.
     * @param restaurantId The ID of the restaurant whose categories are to be fetched.
     * @return ResponseEntity containing a list of categories for the specified restaurant.
     */
    @GetMapping(Constants.GET_CATEGORIES_ENDPOINT)
    public ResponseEntity<List<Category>> getCategories(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Categories for restaurant ID: {}", restaurantId);
        List<Category> categories = categoryService.getAllCategoriesOfRestaurant(restaurantId);
        logger.info("Fetched Categories for restaurant ID: {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * Deletes a category from a restaurant.
     * @param userId The ID of the user performing the deletion.
     * @param categoryId The ID of the category to be deleted.
     * @return ResponseEntity containing the status message of the operation.
     */
    @DeleteMapping(Constants.DELETE_CATEGORY_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> deleteCategory(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @Min(value = 1, message = "Valid Category ID required") long categoryId) {
        logger.info("Deleting Category : {}", categoryId);
        String message = categoryService.deleteCategory(userId, categoryId);
        logger.info("Deleted Category : {}", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Updates the details of an existing category.
     * @param categoryId The ID of the category to be updated.
     * @param updateCategoryDTO DTO containing the updated details of the category.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PutMapping(Constants.UPDATE_CATEGORY_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> updateCategory(
            @PathVariable("categoryId") long categoryId,
            @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        logger.info("Updating Category : {}", updateCategoryDTO);
        String message = categoryService.updateCategory(categoryId, updateCategoryDTO);
        logger.info("Updated Category : {}", updateCategoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Adds a new food item to a restaurant.
     * @param foodItemInDTO DTO containing the details of the food item to be added.
     * @param image Optional image file for the food item.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PostMapping(Constants.ADD_FOOD_ITEM_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addFoodItem(
            @ModelAttribute @Valid FoodItemInDTO foodItemInDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        logger.info("Adding new food item.");
        String message = foodItemService.addFoodItem(foodItemInDTO, image);
        logger.info("Added new food item.");
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Updates the details of an existing food item.
     * @param fooditemId The ID of the food item to be updated.
     * @param updateFoodItemInDTO DTO containing the updated details of the food item.
     * @param image Optional image file for the food item.
     * @return ResponseEntity containing the status message of the operation.
     */
    @PutMapping(Constants.UPDATE_FOOD_ITEM_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> updateFoodItem(
            @PathVariable("foodItemId") long fooditemId,
            @ModelAttribute @Valid UpdateFoodItemInDTO updateFoodItemInDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        logger.info("Updating food item : {}", fooditemId);
        String message = foodItemService.updateFoodItem(fooditemId, updateFoodItemInDTO, image);
        logger.info("Updated food item : {}", fooditemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Deletes a food item from a restaurant.
     * @param userId The ID of the user performing the deletion.
     * @param foodId The ID of the food item to be deleted.
     * @return ResponseEntity containing the status message of the operation.
     */
    @DeleteMapping(Constants.DELETE_FOOD_ITEM)
    public ResponseEntity<RequestSuccessOutDTO> deleteFoodItem(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @Min(value = 1, message = "Valid Food ID required") long foodId) {
        logger.info("Deleting Food item : {}", foodId);
        String message = foodItemService.deleteFoodItem(userId, foodId);
        logger.info("Deleted Food item : {}", foodId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Retrieves all food items for a specific restaurant.
     * @param restaurantId The ID of the restaurant whose food items are to be fetched.
     * @return ResponseEntity containing a list of food items for the specified restaurant.
     */
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_RESTAURANT)
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurant(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Food items of restaurant: {}", restaurantId);
        List<FoodItem> foodItems = foodItemService.getAllFoodItemsOfRestaurant(restaurantId);
        logger.info("Fetched Food items of restaurant: {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItems);
    }

    /**
     * Retrieves all food items for a specific category.
     * @param categoryId The ID of the category whose food items are to be fetched.
     * @return ResponseEntity containing a list of food items for the specified category.
     */
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_CATEGORY)
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable("categoryId") long categoryId) {
        logger.info("Fetching Food items of category: {}", categoryId);
        List<FoodItem> foodItems = foodItemService.getFoodItemsByCategory(categoryId);
        logger.info("Fetched Food items of category: {}", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItems);
    }

    /**
     * Retrieves a food item by its ID.
     * @param foodId The ID of the food item to retrieve.
     * @return ResponseEntity containing the details of the food item.
     */
    @GetMapping("food/id")
    public ResponseEntity<FoodItem> getFoodItemById(@RequestParam long foodId) {
        logger.info("Fetching Food item of ID: {}", foodId);
        FoodItem foodItem = foodItemService.getByFoodId(foodId);
        logger.info("Fetched Food item of ID: {}", foodId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItem);
    }
}
