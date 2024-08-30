package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.FoodAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.InvalidCategoryException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FoodItemServiceImplTest {

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFoodItem_Success() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO(1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});

        when(foodItemRepository.findByCategoryIdAndName(anyLong(), any())).thenReturn(null);
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        String result = foodItemService.addFoodItem(foodItemInDTO);
        assertEquals("Food Item Added Successfully", result);
    }

    @Test
    void testAddFoodItem_FoodAlreadyExists() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO(1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        when(foodItemRepository.findByCategoryIdAndName(anyLong(), any())).thenReturn(new FoodItem());

        assertThrows(FoodAlreadyExistsException.class, () -> foodItemService.addFoodItem(foodItemInDTO));
    }

    @Test
    void testDeleteFoodItem_Success() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO(1L, 1L);
        UserOutDTO user = new UserOutDTO(1L, "owner@example.com", "Owner", "1234567890", Role.OWNER);
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        Category category = new Category(1L, 1L, "Category");
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "abc@gmail.com", "9876543210", "abc street", new byte[]{1,2,3,4,5});

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemService.deleteFoodItem(deleteFoodItemInDTO);
        assertEquals("Deleted Food Item", result);
    }

    @Test
    void testDeleteFoodItem_UserNotFound() {
        DeleteFoodItemInDTO deleteFoodItemInDTO = new DeleteFoodItemInDTO(1L, 1L);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.notFound().build());

        assertThrows(UserNotFoundException.class, () -> foodItemService.deleteFoodItem(deleteFoodItemInDTO));
    }

    @Test
    void testUpdateFoodItem_Success() {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO(1L, 1L, "Updated Burger", "Updated Description", 6.99);
        UserOutDTO user = new UserOutDTO(1L, "owner@example.com", "Owner", "1234567890", Role.OWNER);
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        Category category = new Category(1L, 1L, "Category");
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "abc@gmail.com", "9876543210", "abc street", new byte[]{1,2,3,4,5});

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        String result = foodItemService.updateFoodItem(1L, updateFoodItemInDTO);
        assertEquals("Food Item Updated Successfully", result);
    }

    @Test
    void testUpdateFoodItem_InvalidCategory() {
        UpdateFoodItemInDTO updateFoodItemInDTO = new UpdateFoodItemInDTO(1L, 2L, "Updated Burger", "Updated Description", 6.99);
        UserOutDTO user = new UserOutDTO(1L, "owner@example.com", "Owner", "1234567890", Role.OWNER);
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        Category category = new Category(2L, 1L, "Category");
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "abc@gmail.com", "9876543210", "abc street", new byte[]{1,2,3,4,5});

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenThrow(new InvalidCategoryException(""));
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        assertThrows(InvalidCategoryException.class, () -> foodItemService.updateFoodItem(1L, updateFoodItemInDTO));
    }

    @Test
    void testGetAllFoodItemsOfRestaurant_Success() {
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        when(foodItemRepository.getFoodItemsByResturantId(anyLong())).thenReturn(Arrays.asList(foodItem));

        List<FoodItem> result = foodItemService.getAllFoodItemsOfRestaurant(1L);
        assertEquals(1, result.size());
        assertEquals(foodItem, result.get(0));
    }

    @Test
    void testGetAllFoodItemsOfRestaurant_NoItems() {
        when(foodItemRepository.getFoodItemsByResturantId(anyLong())).thenThrow(new RuntimeException(""));

        assertThrows(RuntimeException.class, () -> foodItemService.getAllFoodItemsOfRestaurant(1L));
    }

    @Test
    void testGetFoodItemsByCategory_Success() {
        FoodItem foodItem = new FoodItem(1L, 1L, "Burger", "Delicious", 5.99, new byte[]{1, 2, 3});
        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(Arrays.asList(foodItem));

        List<FoodItem> result = foodItemService.getFoodItemsByCategory(1L);
        assertEquals(1, result.size());
        assertEquals(foodItem, result.get(0));
    }

    @Test
    void testGetFoodItemsByCategory_NoItems() {
        when(foodItemRepository.findByCategoryId(anyLong())).thenThrow(new RuntimeException(""));

        assertThrows(RuntimeException.class, () -> foodItemService.getFoodItemsByCategory(1L));
    }
}

