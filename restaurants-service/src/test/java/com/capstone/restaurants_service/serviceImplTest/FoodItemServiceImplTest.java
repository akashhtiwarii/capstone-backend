package com.capstone.restaurants_service.serviceImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.FoodItemConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.*;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.FoodItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FoodItemServiceImplTest {

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFoodItem_shouldAddFoodItemSuccessfully() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setName("Burger");
        foodItemInDTO.setCategoryId(1L);

        when(foodItemRepository.findByCategoryIdAndName(anyLong(), anyString())).thenReturn(null);
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(new FoodItem());

        String result = foodItemService.addFoodItem(foodItemInDTO);
        assertEquals("Food Item Added Successfully", result);

        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void addFoodItem_shouldThrowExceptionWhenFoodItemAlreadyExists() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setName("Burger");
        foodItemInDTO.setCategoryId(1L);

        when(foodItemRepository.findByCategoryIdAndName(anyLong(), anyString())).thenReturn(new FoodItem());

        assertThrows(FoodAlreadyExistsException.class, () -> foodItemService.addFoodItem(foodItemInDTO));
    }

    @Test
    void deleteFoodItem_shouldDeleteFoodItemSuccessfully() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO();
        deleteFoodItemInDTO.setFoodId(1L);
        deleteFoodItemInDTO.setUserId(1L);

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.OWNER);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(foodItemRepository.findById(anyLong())).thenReturn(new FoodItem());
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemService.deleteFoodItem(deleteFoodItemInDTO);
        assertEquals("Deleted Food Item", result);

        verify(foodItemRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteFoodItem_shouldThrowExceptionWhenUserIsNotFound() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO();
        deleteFoodItemInDTO.setUserId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        assertThrows(UserNotFoundException.class, () -> foodItemService.deleteFoodItem(deleteFoodItemInDTO));
    }

    @Test
    void updateFoodItem_shouldUpdateFoodItemSuccessfully() {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO();
        updateFoodItemInDTO.setUserId(1L);
        updateFoodItemInDTO.setCategoryId(1L);
        updateFoodItemInDTO.setName("Updated Burger");
        updateFoodItemInDTO.setDescription("Updated description");

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.OWNER);

        Category category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(foodItemRepository.findById(anyLong())).thenReturn(new FoodItem());
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemService.updateFoodItem(1L, updateFoodItemInDTO);
        assertEquals("Food Item Updated Successfully", result);

        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void getAllFoodItemsOfRestaurant_shouldReturnListOfFoodItems() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(1L);
        categories.add(category);

        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem());

        when(categoryRepository.findByRestaurantId(anyLong())).thenReturn(categories);
        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(foodItems);

        List<FoodItem> result = foodItemService.getAllFoodItemsOfRestaurant(1L);
        assertFalse(result.isEmpty());

        verify(foodItemRepository, times(1)).findByCategoryId(anyLong());
    }

    @Test
    void getFoodItemsByCategory_shouldReturnListOfFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem());

        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(foodItems);

        List<FoodItem> result = foodItemService.getFoodItemsByCategory(1L);
        assertFalse(result.isEmpty());

        verify(foodItemRepository, times(1)).findByCategoryId(anyLong());
    }
}
