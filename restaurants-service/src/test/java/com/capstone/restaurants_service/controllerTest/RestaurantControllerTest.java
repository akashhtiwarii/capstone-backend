package com.capstone.restaurants_service.controllerTest;

import com.capstone.restaurants_service.controller.RestaurantController;
import com.capstone.restaurants_service.dto.*;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.service.CategoryService;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.service.RestaurantService;
import com.capstone.restaurants_service.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private FoodItemService foodItemService;

    @Mock
    private MultipartFile image;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "9876543210", "Test Address");
        when(restaurantService.save(restaurantInDTO, image)).thenReturn("Restaurant added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addRestaurant(restaurantInDTO, image);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant added successfully", response.getBody().getMessage());
    }

    @Test
    public void testGetRestaurantById() {
        Restaurant restaurant = new Restaurant(1L, 1L, "Test Restaurant", "test@gmail.com", "9876543210", "Test Address", new byte[0]);
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    public void testGetOwnerRestaurants() {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant(1L, 1L, "Restaurant 1", "test1@gmail.com", "9876543210", "Address 1", new byte[0]),
                new Restaurant(2L, 2L, "Restaurant 2", "test2@gmail.com", "9876543211", "Address 2", new byte[0])
        );
        when(restaurantService.findByOwnerId(1L)).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getOwnerRestaurants(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    public void testGetRestaurants() {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant(1L, 1L, "Restaurant 1", "test1@gmail.com", "9876543210", "Address 1", new byte[0]),
                new Restaurant(2L, 2L, "Restaurant 2", "test2@gmail.com", "9876543211", "Address 2", new byte[0])
        );
        when(restaurantService.findAll()).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    public void testAddCategory() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Test Category");
        when(categoryService.addCategory(categoryInDTO)).thenReturn("Category added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addCategory(categoryInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody().getMessage());
    }

    @Test
    public void testUpdateRestaurant() {
        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO(1L, 1L, "Updated Restaurant", "test@gmail.com", "9876543210", "Updated Address");
        when(restaurantService.updateRestaurant(updateRestaurantInDTO, image)).thenReturn("Restaurant updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateRestaurant(updateRestaurantInDTO, image);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant updated successfully", response.getBody().getMessage());
    }

    @Test
    public void testGetCategories() {
        List<Category> categories = Arrays.asList(
                new Category(1L, 1L, "Category 1"),
                new Category(2L, 1L, "Category 2")
        );
        when(categoryService.getAllCategoriesOfRestaurant(1L)).thenReturn(categories);

        ResponseEntity<List<Category>> response = restaurantController.getCategories(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    public void testDeleteCategory() {
        when(categoryService.deleteCategory(1L, 1L)).thenReturn("Category deleted successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.deleteCategory(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody().getMessage());
    }

    @Test
    public void testUpdateCategory() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();
        updateCategoryDTO.setName("Updated Category");
        when(categoryService.updateCategory(1L, updateCategoryDTO)).thenReturn("Category updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateCategory(1L, updateCategoryDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody().getMessage());
    }

    @Test
    public void testAddFoodItem() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO(1L, 1L, "Test Food", "Description", 100.0);
        when(foodItemService.addFoodItem(foodItemInDTO, image)).thenReturn("Food item added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addFoodItem(foodItemInDTO, image);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item added successfully", response.getBody().getMessage());
    }

    @Test
    public void testUpdateFoodItem() {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO(1L, 1L, "Updated Food", "description", 120.0);
        when(foodItemService.updateFoodItem(1L, updateFoodItemInDTO, image)).thenReturn("Food item updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateFoodItem(1L, updateFoodItemInDTO, image);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item updated successfully", response.getBody().getMessage());
    }

    @Test
    public void testDeleteFoodItem() {
        when(foodItemService.deleteFoodItem(1L, 1L)).thenReturn("Food item deleted successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.deleteFoodItem(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item deleted successfully", response.getBody().getMessage());
    }

    @Test
    public void testGetFoodItemsByRestaurant() {
        List<FoodItem> foodItems = Arrays.asList(
                new FoodItem(1L, 1L, "Food 1", "description", 100.0, new byte[0]),
                new FoodItem(2L, 1L, "Food 1", "description", 100.0, new byte[0])
        );
        when(foodItemService.getAllFoodItemsOfRestaurant(1L)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByRestaurant(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }

    @Test
    public void testGetFoodItemsByCategory() {
        List<FoodItem> foodItems = Arrays.asList(
                new FoodItem(1L, 1L, "Food 1", "description", 100.0, new byte[0]),
                new FoodItem(2L, 1L, "Food 1", "description", 100.0, new byte[0])
        );
        when(foodItemService.getFoodItemsByCategory(1L)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByCategory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }

    @Test
    public void testGetFoodItemById() {
        FoodItem foodItem = new FoodItem(1L, 1L, "Food 1", "description", 100.0, new byte[0]);
        when(foodItemService.getByFoodId(1L)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = restaurantController.getFoodItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItem, response.getBody());
    }
}
