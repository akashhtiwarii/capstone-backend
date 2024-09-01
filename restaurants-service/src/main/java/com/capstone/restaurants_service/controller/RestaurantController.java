package com.capstone.restaurants_service.controller;

import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.InDTO.DeleteCategoryInDTO;
import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.GetAllCategoriesInDTO;
import com.capstone.restaurants_service.InDTO.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.InDTO.UpdateCategoryDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
     * Add new restaurant.
     * @param restaurantInDTO request parameter
     * @return a string message
     */
    @PostMapping(Constants.ADD_RESTAURANT_ENDPOINT)
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantInDTO restaurantInDTO) {
        logger.info("Adding new Restaurant : {}", restaurantInDTO.getName());
        String message = restaurantService.save(restaurantInDTO);
        logger.info("Added Restaurant : {}", restaurantInDTO.getName());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Get Restaurant By ID.
     * @param restaurantId
     * @return Restaurant
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("restaurantId") long restaurantId) {
        logger.info("Fetching Restaurant by Id : {}", restaurantId);
        Restaurant restaurant = restaurantService.findById(restaurantId);
        logger.info("Fetched Restaurant by Id : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    /**
     * Get owner Restaurants.
     * @param getOwnerRestaurantsInDTO
     * @return owner restaurants
     */
    @GetMapping(Constants.GET_OWNER_RESTAURANTS)
    public ResponseEntity<Restaurant> getOwnerRestaurans(
            @Valid @RequestBody GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO) {
        logger.info("Fetching Restaurants of ownerID : {}", getOwnerRestaurantsInDTO.getOwnerId());
        Restaurant restaurant = restaurantService.findByOwnerId(getOwnerRestaurantsInDTO);
        logger.info("Fetching Restaurant of ownerId : {}", getOwnerRestaurantsInDTO.getOwnerId());
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
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
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryInDTO categoryInDTO) {
        logger.info("Adding Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        String response = categoryService.addCategory(categoryInDTO);
        logger.info("Added Category for restaurant ID: {}", categoryInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * Get all categories of a restaurant.
     * @param getAllCategoriesInDTO
     * @return List of categories
     */
    @GetMapping(Constants.GET_CATEGORIES_ENDPOINT)
    public ResponseEntity<List<Category>> getCategories(
            @Valid @RequestBody GetAllCategoriesInDTO getAllCategoriesInDTO) {
        logger.info("Fetching Categories for restaurant ID: {}", getAllCategoriesInDTO.getRestaurantId());
        List<Category> categories = categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO);
        logger.info("Fetching Categories for restaurant ID: {}", getAllCategoriesInDTO.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * Delete Category.
     * @param deleteCategoryInDTO
     * @return String message
     */
    @DeleteMapping(Constants.DELETE_CATEGORY_ENDPOINT)
    public ResponseEntity<String> deleteCategory(@Valid @RequestBody DeleteCategoryInDTO deleteCategoryInDTO) {
        logger.info("Deleting Category : {}", deleteCategoryInDTO.getCategoryId());
        String message = categoryService.deleteCategory(deleteCategoryInDTO);
        logger.info("Deleted Category : {}", deleteCategoryInDTO.getCategoryId());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Update Category.
     * @param categoryId
     * @param updateCategoryDTO
     * @return String message
     */
    @PutMapping(Constants.UPDATE_CATEGORY_ENDPOINT + "/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") long categoryId,
                                                 @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        logger.info("Updating Category : {}", updateCategoryDTO);
        String message = categoryService.updateCategory(categoryId, updateCategoryDTO);
        logger.info("Updated Category : {}", updateCategoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    /**
     * Add new Food Item.
     * @param foodItemInDTO
     * @return String message
     */
    @PostMapping(Constants.ADD_FOOD_ITEM_ENDPOINT)
    public ResponseEntity<String> addFoodItem(@Valid @RequestBody FoodItemInDTO foodItemInDTO) {
        logger.info("Adding new food item.");
        String message = foodItemService.addFoodItem(foodItemInDTO);
        logger.info("Added new food item.");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Update Food Item.
     * @param fooditemId
     * @param updateFoodItemInDTO
     * @return String message
     */
    @PutMapping(Constants.UPDATE_FOOD_ITEM_ENDPOINT + "/{foodItemId}")
    public ResponseEntity<String> updateFoodItem(@PathVariable("foodItemId") long fooditemId,
                                                 @Valid @RequestBody UpdateFoodItemInDTO updateFoodItemInDTO) {
        logger.info("Updating food item : {}", fooditemId);
        String message = foodItemService.updateFoodItem(fooditemId, updateFoodItemInDTO);
        logger.info("Updating food item : {}", fooditemId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Delete Food Item.
     * @param deleteFoodItemInDTO
     * @return String message
     */
    @DeleteMapping(Constants.DELETE_FOOD_ITEM)
    public ResponseEntity<String> deleteFooditem(DeleteFoodItemInDTO deleteFoodItemInDTO) {
        logger.info("Deleting Food item : {}", deleteFoodItemInDTO.getFoodId());
        String message = foodItemService.deleteFoodItem(deleteFoodItemInDTO);
        logger.info("Deleted Food item : {}", deleteFoodItemInDTO.getFoodId());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Get food by restaurant.
     * @param restaurantId
     * @return food items
     */
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_RESTAURANT + "/{restaurantId}")
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
    @GetMapping(Constants.GET_FOOD_ITEMS_BY_CATEGORY + "/{categoryId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable("categoryId") long categoryId) {
        logger.info("Fetching Food items of category: {}", categoryId);
        List<FoodItem> foodItems = foodItemService.getFoodItemsByCategory(categoryId);
        logger.info("Fetched Food items of category: {}", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItems);
    }
}
