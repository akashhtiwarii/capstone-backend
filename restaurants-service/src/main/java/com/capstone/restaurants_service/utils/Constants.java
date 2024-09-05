package com.capstone.restaurants_service.utils;

/**
 * Constants for storing constants used throughout.
 */
public class Constants {
    /**
     * Phone number length.
     */
    public static final int PHONE_NUMBER_LENGTH = 10;
    /**
     * Restaurant Endpoint.
     */
    public static final String RESTAURANT_ENDPOINT = "/restaurant";
    /**
     * Add Restaurant Endpoint.
     */
    public static final String ADD_RESTAURANT_ENDPOINT = "/add";
    /**
     * Update Restaurant Endpoint.
     */
    public static final String UPDATE_RESTAURANT_ENDPOINT = "/update";
    /**
     * Get All Restaurants Endpoint.
     */
    public static final String GET_RESTAURANTS = "/all";
    /**
     * Get Owner Restaurants Endpoint.
     */
    public static final String GET_OWNER_RESTAURANTS = "/owner";
    /**
     * Get Restaurant by ID Endpoint.
     */
    public static final String GET_RESTAURANT_BY_ID = "/{restaurantId}";
    /**
     * Get Categories Endpoint.
     */
    public static final String GET_CATEGORIES_ENDPOINT = "/categories/{restaurantId}";
    /**
     * Add Category Endpoint.
     */
    public static final String ADD_CATEGORIES_ENDPOINT = "/categories/add";
    /**
     * Add Food Item Endpoint.
     */
    public static final String ADD_FOOD_ITEM_ENDPOINT = "/food/add";
    /**
     * Delete category Endpoint.
     */
    public static final String DELETE_CATEGORY_ENDPOINT = "/categories/delete";
    /**
     * Update category Endpoint.
     */
    public static final String UPDATE_CATEGORY_ENDPOINT = "/categories/update/{categoryId}";
    /**
     * Update Food Item Endpoint.
     */
    public static final String UPDATE_FOOD_ITEM_ENDPOINT = "/food/update/{foodItemId}";
    /**
     * Delete Food Item Endpoint.
     */
    public static final String DELETE_FOOD_ITEM = "/food/delete";
    /**
     * Get Food By Restaurant Endpoint.
     */
    public static final String GET_FOOD_ITEMS_BY_RESTAURANT = "/restaurantfood/{restaurantId}";
    /**
     * Get Food By Category Endpoint.
     */
    public static final String GET_FOOD_ITEMS_BY_CATEGORY = "/categoryfood/{categoryId}";
    /**
     * user service.
     */
    public static final String USER_SERVICE = "users-service";
    /**
     * User service endpoint.
     */
    public static final String USER_SERVICE_ENDPOINT = "http://localhost:8081/user";
    /**
     * User not found.
     */
    public static final String USER_NOT_FOUND = "User Not Found";
    /**
     * You cannot add a category.
     */
    public static final String YOU_CANNOT_ADD_A_CATEGORY = "You cannot Add a category";
    /**
     * Message indicating that a restaurant does not exist.
     */
    public static final String RESTAURANT_DOES_NOT_EXISTS = "Restaurant does not exists";

    /**
     * Message indicating that the category is already present.
     */
    public static final String CATEGORY_ALREADY_PRESENT = "Category is already present";

    /**
     * Message indicating that the category has been added successfully.
     */
    public static final String CATEGORY_ADDED_SUCCESSFULLY = "Category Added Successfully";

    /**
     * Message indicating that an unexpected error occurred.
     */
    public static final String UNEXPECTED_ERROR_OCCURRED = "An unexpected error occurred: ";

    /**
     * Message indicating that there are no restaurants available.
     */
    public static final String NO_RESTAURANTS = "You do not have any restaurants";

    /**
     * Message indicating that there are no categories for the restaurant.
     */
    public static final String NO_CATEGORIES_FOR_RESTAURANT = "No Categories for your restaurant";

    /**
     * Message indicating that the category cannot be updated.
     */
    public static final String YOU_CANNOT_UPDATE_CATEGORY = "You cannot update a category";

    /**
     * Message indicating that the category was not found.
     */
    public static final String CATEGORY_NOT_FOUND = "Category Not Found";

    /**
     * Message indicating that the category has been updated successfully.
     */
    public static final String CATEGORY_UPDATED_SUCCESSFULLY = "Category updated successfully";

    /**
     * Message indicating that the category cannot be deleted.
     */
    public static final String YOU_CANNOT_DELETE_CATEGORY = "You cannot delete a category";

    /**
     * Message indicating that the category has been deleted successfully.
     */
    public static final String CATEGORY_DELETED = "Category Deleted Successfully";

    /**
     * Message indicating that the restaurant cannot be added.
     */
    public static final String YOU_CANNOT_ADD_RESTAURANT = "You cannot add a restaurant";

    /**
     * Message indicating that the restaurant cannot be updated.
     */
    public static final String YOU_CANNOT_UPDATE_RESTAURANT = "You cannot update a restaurant";

    /**
     * Message indicating that the email ID already exists.
     */
    public static final String EMAIL_ALREADY_EXISTS = "Email ID already Exists";

    /**
     * Message indicating that the restaurant has been added successfully.
     */
    public static final String RESTAURANT_ADDED_SUCCESSFULLY = "Restaurant added successfully";

    /**
     * Message indicating that the restaurant has been updated successfully.
     */
    public static final String RESTAURANT_UPDATED_SUCCESSFULLY = "Restaurant updated successfully";

    /**
     * Message indicating that image upload failed.
     */
    public static final String FAILED_TO_UPLOAD_IMAGE = "Failed to upload image: ";

    /**
     * Message indicating that the food item was not found.
     */
    public static final String FOOD_NOT_FOUND = "Food Item Not Found";

    /**
     * Message indicating that the food item cannot be added.
     */
    public static final String YOU_CANNOT_ADD_FOOD = "You cannot Add a food item";

    /**
     * Message indicating that the food item has been added successfully.
     */
    public static final String FOOD_ADDED_SUCCESSFULLY = "Food Item Added Successfully";

    /**
     * Message indicating that the food item has been updated successfully.
     */
    public static final String FOOD_UPDATED_SUCCESSFULLY = "Food Item Updated Successfully";

    /**
     * Message indicating that the food item has been deleted successfully.
     */
    public static final String FOOD_DELETED_SUCCESSFULLY = "Food Item Deleted Successfully";

    /**
     * Message indicating that the restaurant was not found.
     */
    public static final String RESTAURANT_NOT_FOUND = "Restaurant Not Found";

    /**
     * Message indicating that the food item is already in the menu.
     */
    public static final String FOOD_ALREADY_PRESENT = "Food item already in the menu";

    /**
     * Message indicating that the food item cannot be updated.
     */
    public static final String YOU_CANNOT_UPDATE_FOOD = "You cannot update a food item";

    /**
     * Message indicating that the food item cannot be deleted.
     */
    public static final String YOU_CANNOT_DELETE_FOOD = "You cannot delete a food item";

    /**
     * Message indicating that the category is invalid.
     */
    public static final String INVALID_CATEGORY = "Invalid Category";
}
