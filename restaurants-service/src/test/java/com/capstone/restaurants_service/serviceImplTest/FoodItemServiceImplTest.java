package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.DeleteFoodItemInDTO;
import com.capstone.restaurants_service.InDTO.FoodItemInDTO;
import com.capstone.restaurants_service.InDTO.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.CategoryNotFoundException;
import com.capstone.restaurants_service.exceptions.FoodItemNotFoundException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FoodItemServiceImplTest {

    @InjectMocks
    private FoodItemServiceImpl foodItemServiceImpl;

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
    void addFoodItemCategoryNotFoundShouldThrowException() {

        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> {
            foodItemServiceImpl.addFoodItem(new FoodItemInDTO());
        });
    }

    @Test
    void addFoodItemSuccessShouldReturnSuccessMessage() {

        when(categoryRepository.existsById(anyLong())).thenReturn(true);
        when(foodItemRepository.findByCategoryIdAndName(anyLong(), anyString())).thenReturn(null);


        String result = foodItemServiceImpl.addFoodItem(new FoodItemInDTO());


        assertEquals("Food Item Added Successfully", result);
        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void deleteFoodItemUserNotFoundShouldThrowException() {

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.of(Optional.empty()));

        assertThrows(UserNotFoundException.class, () -> {
            foodItemServiceImpl.deleteFoodItem(new DeleteFoodItemInDTO());
        });
    }

    @Test
    void deleteFoodItemUserNotOwnerShouldThrowException() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        assertThrows(UserNotValidException.class, () -> {
            foodItemServiceImpl.deleteFoodItem(new DeleteFoodItemInDTO());
        });
    }

    @Test
    void deleteFoodItemFoodItemNotFoundShouldThrowException() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(null);

        assertThrows(FoodItemNotFoundException.class, () -> {
            foodItemServiceImpl.deleteFoodItem(new DeleteFoodItemInDTO());
        });
    }

    @Test
    void deleteFoodItemSuccessShouldReturnSuccessMessage() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        user.setUserId(1L);

        Category category = new Category();
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);


        String result = foodItemServiceImpl.deleteFoodItem(new DeleteFoodItemInDTO());


        assertEquals("Deleted Food Item", result);
        verify(foodItemRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateFoodItemUserNotFoundShouldThrowException() {

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.of(Optional.empty()));

        assertThrows(UserNotFoundException.class, () -> {
            foodItemServiceImpl.updateFoodItem(1L, new UpdateFoodItemInDTO());
        });
    }

    @Test
    void updateFoodItemUserNotOwnerShouldThrowException() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        assertThrows(UserNotValidException.class, () -> {
            foodItemServiceImpl.updateFoodItem(1L, new UpdateFoodItemInDTO());
        });
    }

    @Test
    void updateFoodItemFoodItemNotFoundShouldThrowException() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(null);

        assertThrows(FoodItemNotFoundException.class, () -> {
            foodItemServiceImpl.updateFoodItem(1L, new UpdateFoodItemInDTO());
        });
    }

    @Test
    void updateFoodItemSuccessShouldReturnSuccessMessage() {

        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        user.setUserId(1L);

        Category category = new Category();
        category.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(foodItemRepository.findById(anyLong())).thenReturn(foodItem);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);


        String result = foodItemServiceImpl.updateFoodItem(1L, new UpdateFoodItemInDTO());


        assertEquals("Food Item Updated Successfully", result);
        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void getAllFoodItemsOfRestaurantRestaurantNotFoundShouldThrowException() {

        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> {
            foodItemServiceImpl.getAllFoodItemsOfRestaurant(1L);
        });
    }

    @Test
    void getAllFoodItemsOfRestaurantNoCategoryShouldThrowException() {

        when(restaurantRepository.findById(anyLong())).thenReturn(new Restaurant());
        when(categoryRepository.findByRestaurantId(anyLong())).thenReturn(new ArrayList<>());

        assertThrows(CategoryNotFoundException.class, () -> {
            foodItemServiceImpl.getAllFoodItemsOfRestaurant(1L);
        });
    }

    @Test
    void getAllFoodItemsOfRestaurantNoFoodItemsShouldThrowException() {

        Restaurant restaurant = new Restaurant();
        Category category = new Category();
        category.setCategoryId(1L);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(anyLong())).thenReturn(categories);
        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(new ArrayList<>());

        assertThrows(FoodItemNotFoundException.class, () -> {
            foodItemServiceImpl.getAllFoodItemsOfRestaurant(1L);
        });
    }

    @Test
    void getAllFoodItemsOfRestaurantSuccessShouldReturnFoodItems() {

        Restaurant restaurant = new Restaurant();
        Category category = new Category();
        category.setCategoryId(1L);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        List<FoodItem> foodItems = new ArrayList<>();
        FoodItem foodItem = new FoodItem();
        foodItem.setName("Pizza");
        foodItems.add(foodItem);

        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(anyLong())).thenReturn(categories);
        when(foodItemRepository.findByCategoryId(anyLong())).thenReturn(foodItems);


        List<FoodItem> result = foodItemServiceImpl.getAllFoodItemsOfRestaurant(1L);


        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Pizza", result.get(0).getName());
    }
}
