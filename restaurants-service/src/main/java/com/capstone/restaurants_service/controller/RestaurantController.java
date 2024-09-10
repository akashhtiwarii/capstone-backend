package com.capstone.restaurants_service.controller;

import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.service.CategoryService;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.service.RestaurantService;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.FoodItemWithImageInDTO;
import com.capstone.restaurants_service.dto.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.RestaurantWithImageInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import com.capstone.restaurants_service.dto.RequestSuccessOutDTO;
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
 * Restaurant Controller.
 */
@RestController
@RequestMapping(Constants.RESTAURANT_ENDPOINT)
@CrossOrigin
public class RestaurantController {
    /**
     * Restaurant Service for accessing restaurants table operations.
     */
    @Autowired
    private RestaurantService restaurantService;
    /**
     * Category Service for accessing categories table operations.
     */
    @Autowired
    private CategoryService categoryService;
    /**
     * Food Item Service for accessing food_items table operations.
     */
    @Autowired
    private FoodItemService foodItemService;
    /**
     * Logger for logging.
     */
    private final Logger logger = LogManager.getLogger(RestaurantController.class);

    /**
     * Add Restaurant.
     * @param restaurantWithImageInDTO
     * @return String message
     */
    @PostMapping(Constants.ADD_RESTAURANT_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addRestaurant(
            @Valid @ModelAttribute RestaurantWithImageInDTO restaurantWithImageInDTO) {
        logger.info("Adding Restaurant : {}", restaurantWithImageInDTO.getRestaurant().getName());
        RestaurantInDTO restaurant = restaurantWithImageInDTO.getRestaurant();
        logger.info("Restaurant Details Fetched");
        MultipartFile image = restaurantWithImageInDTO.getImage();
        logger.info("Image Fetched");
        String message = restaurantService.save(restaurant, image);
        logger.info("Restaurant Added");
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Get Restaurant By ID.
     * @param restaurantId
     * @return Restaurant
     */
    @GetMapping(Constants.GET_RESTAURANT_BY_ID)
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Restaurant by Id : {}", restaurantId);
        Restaurant restaurant = restaurantService.findById(restaurantId);
        logger.info("Fetched Restaurant by Id : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    /**
     * Get owner Restaurants.
     * @param ownerId
     * @return owner restaurants
     */
    @GetMapping(Constants.GET_OWNER_RESTAURANTS)
    public ResponseEntity<List<Restaurant>> getOwnerRestaurants(
            @RequestParam @Min(value = 1, message = "Valid Owner ID required") long ownerId) {
        logger.info("Fetching Restaurants of ownerID : {}", ownerId);
        List<Restaurant> restaurants = restaurantService.findByOwnerId(ownerId);
        logger.info("Fetching Restaurant of ownerId : {}", ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    /**
     * Get all Restaurants.
     * @return Restaurants
     */
    @GetMapping(Constants.GET_RESTAURANTS)
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        logger.info("Fetching Restaurants");
        List<Restaurant> restaurants = restaurantService.findAll();
        logger.info("Fetched Restaurants");
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }
    /**
     * Add new Category.
     * @param categoryInDTO
     * @return message
     */
    @PostMapping(Constants.ADD_CATEGORIES_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addCategory(@Valid @RequestBody CategoryInDTO categoryInDTO) {
        logger.info("Adding Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        String response = categoryService.addCategory(categoryInDTO);
        logger.info("Added Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Update Restaurant.
     *
     * @param updateRestaurantInDTO
     * @return String message
     */
    @PutMapping(Constants.UPDATE_RESTAURANT_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> updateRestaurant(
            @Valid @ModelAttribute UpdateRestaurantInDTO updateRestaurantInDTO) {
        logger.info("Updating Restaurant for restaurant ID: {}", updateRestaurantInDTO.getRestaurantId());
        String response = restaurantService.updateRestaurant(updateRestaurantInDTO);
        logger.info("Updated Restaurant for restaurant ID: {}", updateRestaurantInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }
    /**
     * Get all categories of a restaurant.
     * @param restaurantId
     * @return List of categories
     */
    @GetMapping(Constants.GET_CATEGORIES_ENDPOINT)
    public ResponseEntity<List<Category>> getCategories(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Categories for restaurant ID: {}", restaurantId);
        List<Category> categories = categoryService.getAllCategoriesOfRestaurant(restaurantId);
        logger.info("Fetching Categories for restaurant ID: {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * Delete Category.
     * @param categoryId
     * @param userId
     * @return String message
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
     * Update Category.
     *
     * @param categoryId
     * @param updateCategoryDTO
     * @return String message
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
     * Add new Food Item.
     *
     * @param foodItemWithImageInDTO
     * @return String message
     */
    @PostMapping(Constants.ADD_FOOD_ITEM_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addFoodItem(
            @Valid @ModelAttribute FoodItemWithImageInDTO foodItemWithImageInDTO) {
        logger.info("Adding new food item.");
        FoodItemInDTO foodItemInDTO = foodItemWithImageInDTO.getFoodItem();
        MultipartFile image = foodItemWithImageInDTO.getImage();
        String message = foodItemService.addFoodItem(foodItemInDTO, image);
        logger.info("Added new food item.");
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Update Food Item.
     *
     * @param fooditemId
     * @param updateFoodItemInDTO
     * @return String message
     */
    @PutMapping(Constants.UPDATE_FOOD_ITEM_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> updateFoodItem(
            @PathVariable("foodItemId") long fooditemId,
            @Valid @ModelAttribute UpdateFoodItemInDTO updateFoodItemInDTO) {
        logger.info("Updating food item : {}", fooditemId);
        String message = foodItemService.updateFoodItem(fooditemId, updateFoodItemInDTO);
        logger.info("Updating food item : {}", fooditemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Delete Food Item.
     * @param foodId
     * @param userId
     * @return String message
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
     * Get food by restaurant.
     * @param restaurantId
     * @return food items
     */
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_RESTAURANT)
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurant(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Food items of restaurant: {}", restaurantId);
        List<FoodItem> foodItems = foodItemService.getAllFoodItemsOfRestaurant(restaurantId);
        logger.info("Fetched Food items of restaurant: {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItems);
    }

    /**
     * Get food by category.
     * @param categoryId
     * @return food items
     */
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_CATEGORY)
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable("categoryId") long categoryId) {
        logger.info("Fetching Food items of category: {}", categoryId);
        List<FoodItem> foodItems = foodItemService.getFoodItemsByCategory(categoryId);
        logger.info("Fetched Food items of category: {}", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItems);
    }

    @GetMapping("food/id")
    public ResponseEntity<FoodItem> getFoodItemById(@RequestParam long foodId) {
        logger.info("Fetching Food item of ID: {}", foodId);
        FoodItem foodItem = foodItemService.getByFoodId(foodId);
        logger.info("Fetched Food item of ID: {}", foodId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItem);
    }
}
