package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.FoodItemServiceImpl;
import com.capstone.restaurants_service.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FoodItemServiceImplTest {

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private MultipartFile image;

    @InjectMocks
    private FoodItemServiceImpl foodItemServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFoodItemValidDataReturnsSuccessMessage() throws Exception {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setLoggedInOwnerId(1L);
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("burger");

        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        user.setRole(Role.OWNER);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(foodItemRepository.findByCategoryIdAndName(anyLong(), anyString())).thenReturn(null);

        String result = foodItemServiceImpl.addFoodItem(foodItemInDTO, image);

        assertEquals(Constants.FOOD_ADDED_SUCCESSFULLY, result);
        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void addFoodItemExistingFoodItemThrowsException() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setLoggedInOwnerId(1L);
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("burger");

        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        user.setRole(Role.OWNER);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        FoodItem existingFoodItem = new FoodItem();

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(foodItemRepository.findByCategoryIdAndName(anyLong(), anyString())).thenReturn(existingFoodItem);

        assertThrows(ResourceAlreadyExistsException.class, () -> foodItemServiceImpl.addFoodItem(foodItemInDTO, image));
    }

    @Test
    void deleteFoodItemValidUserReturnsSuccessMessage() {
        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        user.setRole(Role.OWNER);

        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(1L);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemServiceImpl.deleteFoodItem(1L, 1L);

        assertEquals(Constants.FOOD_DELETED_SUCCESSFULLY, result);
        verify(foodItemRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateFoodItemValidDataReturnsSuccessMessage() throws Exception {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO();
        updateFoodItemInDTO.setLoggedInOwnerId(1L);
        updateFoodItemInDTO.setCategoryId(1L);
        updateFoodItemInDTO.setName("Updated Name");
        updateFoodItemInDTO.setDescription("Updated Description");
        updateFoodItemInDTO.setPrice(15.99);

        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        user.setRole(Role.OWNER);

        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(1L);

        Category category = new Category();
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemServiceImpl.updateFoodItem(1L, updateFoodItemInDTO, image);

        assertEquals(Constants.FOOD_UPDATED_SUCCESSFULLY, result);
        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void getAllFoodItemsOfRestaurantValidRestaurantReturnsFoodItems() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(1L);

        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(foodItem);

        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(anyLong())).thenReturn(categories);
        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(foodItems);

        List<FoodItem> result = foodItemServiceImpl.getAllFoodItemsOfRestaurant(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getByFoodIdValidFoodIdReturnsFoodItem() {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodId(1L);

        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);

        FoodItem result = foodItemServiceImpl.getByFoodId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getFoodId());
    }

    @Test
    public void testGetFoodItemsByCategorySuccessMultipleItems() {
        long categoryId = 1L;
        List<FoodItem> foodItems = Arrays.asList(
                new FoodItem(1L, categoryId, "Pizza", "Delicious pizza", 10.99, new byte[0]),
                new FoodItem(2L, categoryId, "Pizza", "Delicious pizza", 10.99, new byte[0])
        );
        when(foodItemRepository.findByCategoryId(categoryId)).thenReturn(foodItems);

        List<FoodItem> result = foodItemServiceImpl.getFoodItemsByCategory(categoryId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(foodItemRepository, times(1)).findByCategoryId(categoryId);
    }

}