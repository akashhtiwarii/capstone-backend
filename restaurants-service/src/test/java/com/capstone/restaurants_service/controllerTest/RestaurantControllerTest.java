package com.capstone.restaurants_service.controllerTest;

import com.capstone.restaurants_service.controller.RestaurantController;
import com.capstone.restaurants_service.dto.InDTO.*;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.service.CategoryService;
import com.capstone.restaurants_service.service.FoodItemService;
import com.capstone.restaurants_service.service.RestaurantService;
import com.capstone.restaurants_service.dto.OutDTO.RequestSuccessOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        MultipartFile image = mock(MultipartFile.class);
        RestaurantWithImageInDTO dto = new RestaurantWithImageInDTO(restaurantInDTO, image);
        when(restaurantService.save(any(RestaurantInDTO.class), any(MultipartFile.class))).thenReturn("Restaurant added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addRestaurant(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant added successfully", response.getBody().getMessage());
    }

    @Test
    void testGetRestaurantById() {
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(restaurantId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    void testGetOwnerRestaurants() {
        GetOwnerRestaurantsInDTO dto = new GetOwnerRestaurantsInDTO();
        List<Restaurant> restaurants = new ArrayList<>();
        when(restaurantService.findByOwnerId(dto)).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getOwnerRestaurants(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
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
        when(categoryService.addCategory(categoryInDTO)).thenReturn("Category added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addCategory(categoryInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody().getMessage());
    }

    @Test
    void testUpdateRestaurant() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO();
        when(restaurantService.updateRestaurant(dto)).thenReturn("Restaurant updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateRestaurant(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant updated successfully", response.getBody().getMessage());
    }

    @Test
    void testGetCategories() {
        long restaurantId = 1L;
        List<Category> categories = new ArrayList<>();
        when(categoryService.getAllCategoriesOfRestaurant(restaurantId)).thenReturn(categories);

        ResponseEntity<List<Category>> response = restaurantController.getCategories(restaurantId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void testDeleteCategory() {
        when(categoryService.deleteCategory(1L,1L)).thenReturn("Category deleted successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.deleteCategory(1L,1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody().getMessage());
    }

    @Test
    void testUpdateCategory() {
        long categoryId = 1L;
        UpdateCategoryDTO dto = new UpdateCategoryDTO();
        when(categoryService.updateCategory(categoryId, dto)).thenReturn("Category updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateCategory(categoryId, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody().getMessage());
    }

    @Test
    void testAddFoodItem() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        MultipartFile image = mock(MultipartFile.class);
        FoodItemWithImageInDTO dto = new FoodItemWithImageInDTO(foodItemInDTO, image);
        when(foodItemService.addFoodItem(any(FoodItemInDTO.class), any(MultipartFile.class))).thenReturn("Food item added successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.addFoodItem(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item added successfully", response.getBody().getMessage());
    }

    @Test
    void testUpdateFoodItem() {
        long foodItemId = 1L;
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO();
        when(foodItemService.updateFoodItem(foodItemId, dto)).thenReturn("Food item updated successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.updateFoodItem(foodItemId, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item updated successfully", response.getBody().getMessage());
    }

    @Test
    void testDeleteFoodItem() {
        when(foodItemService.deleteFoodItem(1L,1L)).thenReturn("Food item deleted successfully");

        ResponseEntity<RequestSuccessOutDTO> response = restaurantController.deleteFoodItem(1L,1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Food item deleted successfully", response.getBody().getMessage());
    }

    @Test
    void testGetFoodItemsByRestaurant() {
        long restaurantId = 1L;
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemService.getAllFoodItemsOfRestaurant(restaurantId)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByRestaurant(restaurantId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }

    @Test
    void testGetFoodItemsByCategory() {
        long categoryId = 1L;
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemService.getFoodItemsByCategory(categoryId)).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = restaurantController.getFoodItemsByCategory(categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItems, response.getBody());
    }
}
