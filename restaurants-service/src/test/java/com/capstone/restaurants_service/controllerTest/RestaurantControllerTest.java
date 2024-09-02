package com.capstone.restaurants_service.controllerTest;

import com.capstone.restaurants_service.InDTO.*;
import com.capstone.restaurants_service.controller.RestaurantController;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.service.CategoryService;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private FoodItemService foodItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRestaurantById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    void testGetRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        when(restaurantService.findAll()).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    void testAddCategory() {
        CategoryInDTO categoryInDTO = new CategoryInDTO();
        categoryInDTO.setRestaurantId(1L);
        when(categoryService.addCategory(categoryInDTO)).thenReturn("Category added successfully");

        ResponseEntity<String> response = restaurantController.addCategory(categoryInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody());
    }

    @Test
    void testGetCategories() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO();
        getAllCategoriesInDTO.setRestaurantId(1L);
        List<Category> categories = new ArrayList<>();
        when(categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO)).thenReturn(categories);

        ResponseEntity<List<Category>> response = restaurantController.getCategories(getAllCategoriesInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void testDeleteCategory() {
        DeleteCategoryInDTO deleteCategoryInDTO = new DeleteCategoryInDTO();
        deleteCategoryInDTO.setCategoryId(1L);
        when(categoryService.deleteCategory(deleteCategoryInDTO)).thenReturn("Category deleted successfully");

        ResponseEntity<String> response = restaurantController.deleteCategory(deleteCategoryInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
    }

    @Test
    void testUpdateCategory() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();
        when(categoryService.updateCategory(1L, updateCategoryDTO)).thenReturn("Category updated successfully");

        ResponseEntity<String> response = restaurantController.updateCategory(1L, updateCategoryDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody());
    }

    @Test
    void testAddFoodItem() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        when(foodItemService.addFoodItem(foodItemInDTO)).thenReturn("Food item added successfully");

        ResponseEntity<String> response = restaurantController.addFoodItem(foodItemInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item added successfully", response.getBody());
    }

    @Test
    void testUpdateFoodItem() {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO();
        when(foodItemService.updateFoodItem(1L, updateFoodItemInDTO)).thenReturn("Food item updated successfully");

        ResponseEntity<String> response = restaurantController.updateFoodItem(1L, updateFoodItemInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item updated successfully", response.getBody());
    }

    @Test
    void testDeleteFoodItem() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO();
        deleteFoodItemInDTO.setFoodId(1L);
        when(foodItemService.deleteFoodItem(deleteFoodItemInDTO)).thenReturn("Food item deleted successfully");

        ResponseEntity<String> response = restaurantController.deleteFooditem(deleteFoodItemInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item deleted successfully", response.getBody());
    }

    @Test
    void testGetFoodItemsByRestaurant() {
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemService.getAllFoodItemsOfRestaurant(1L)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByRestaurant(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }

    @Test
    void testGetFoodItemsByCategory() {
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemService.getFoodItemsByCategory(1L)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByCategory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }
}

