package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.*;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.FoodItemRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.FoodItemServiceImpl;
import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    private FoodItemInDTO foodItemInDTO;
    private UserOutDTO userOutDTO;
    private Category category;
    private Restaurant restaurant;
    private UpdateFoodItemInDTO updateFoodItemInDTO;
    private FoodItem existingFoodItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        foodItemInDTO = new FoodItemInDTO();
        foodItemInDTO.setLoggedInOwnerId(1L);
        foodItemInDTO.setCategoryId(1L);
        foodItemInDTO.setName("Pizza");

        userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.OWNER);

        category = new Category();
        category.setCategoryId(1L);
        category.setRestaurantId(1L);

        restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        restaurant.setOwnerId(1L);
        updateFoodItemInDTO = new UpdateFoodItemInDTO();
        updateFoodItemInDTO.setLoggedInOwnerId(1L);
        updateFoodItemInDTO.setCategoryId(2L);
        updateFoodItemInDTO.setName("Updated Food");

        existingFoodItem = new FoodItem();
        existingFoodItem.setFoodId(1L);
        existingFoodItem.setCategoryId(1L);
    }

    @Test
    void testAddFoodItem_Success() {
        MultipartFile image = mock(MultipartFile.class);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(foodItemRepository.findByCategoryIdAndName(1L, "Pizza")).thenReturn(null);

        String result = foodItemService.addFoodItem(foodItemInDTO, image);
        assertEquals("Food Item Added Successfully", result);
        verify(foodItemRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    void testAddFoodItem_UserNotFound() {
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.of(Optional.empty()));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                foodItemService.addFoodItem(foodItemInDTO, null));
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void testAddFoodItem_InvalidUser() {
        userOutDTO.setRole(Role.USER);
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        UserNotValidException exception = assertThrows(UserNotValidException.class, () ->
                foodItemService.addFoodItem(foodItemInDTO, null));
        assertEquals("You cannot Add a food item", exception.getMessage());
    }

    @Test
    void testAddFoodItem_CategoryNotFound() {
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(null);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () ->
                foodItemService.addFoodItem(foodItemInDTO, null));
        assertEquals("Category Does Not Exist", exception.getMessage());
    }

    @Test
    void testAddFoodItem_FoodAlreadyExists() {
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(foodItemRepository.findByCategoryIdAndName(1L, "Pizza")).thenReturn(new FoodItem());

        FoodAlreadyExistsException exception = assertThrows(FoodAlreadyExistsException.class, () ->
                foodItemService.addFoodItem(foodItemInDTO, null));
        assertEquals("Food item already in the menu", exception.getMessage());
    }

    @Test
    void deleteFoodItemShouldDeleteSuccessfullyWhenValidInputs() {
        UserOutDTO userOutDTO = new UserOutDTO(1L,"test@gmail.com","test","9876543210", Role.OWNER);
        FoodItem foodItem = new FoodItem(1L, 1L, "Pizza", "Delicious Pizza", 10.0, null);
        Category category = new Category(1L, 1L,"Fast Food" );
        Restaurant restaurant = new Restaurant(1L, 1L,"Old Restaurant", "old@gmail.com", "1234567890", "Address", null);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(foodItemRepository.findById(1L)).thenReturn(foodItem);
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);


        String result = foodItemService.deleteFoodItem(1L,1L);


        assertEquals("Deleted Food Item", result);
        verify(foodItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateFoodItem_FoodItemNotFound() {
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(foodItemRepository.findById(1L)).thenReturn(null);

        FoodItemNotFoundException exception = assertThrows(FoodItemNotFoundException.class, () ->
                foodItemService.updateFoodItem(1L, updateFoodItemInDTO));
        assertEquals("No Food Item Exists", exception.getMessage());
    }

    @Test
    void testUpdateFoodItem_UnauthorizedUser() {
        userOutDTO.setRole(Role.USER);
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        UserNotValidException exception = assertThrows(UserNotValidException.class, () ->
                foodItemService.updateFoodItem(1L, updateFoodItemInDTO));
        assertEquals("You cannot update a food item", exception.getMessage());
    }

    @Test
    void getAllFoodItemsOfRestaurantShouldReturnFoodItemsWhenRestaurantHasFoodItems() {

        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant(1L, 1L,"Old Restaurant", "old@gmail.com", "1234567890", "Address", null);
        Category category = new Category(1L, 1L,"Fast Food" );
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(1L, 1L, "Pizza", "Delicious Pizza", 10.0, null));

        when(restaurantRepository.findById(restaurantId)).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(restaurantId)).thenReturn(categories);
        when(foodItemRepository.findByCategoryId(1L)).thenReturn(foodItems);


        List<FoodItem> result = foodItemService.getAllFoodItemsOfRestaurant(restaurantId);


        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    @Test
    void getFoodItemsByCategoryShouldReturnFoodItemsWhenCategoryHasFoodItems() {

        long categoryId = 1L;
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(1L, 1L, "Pizza", "Delicious Pizza", 10.0, null));

        when(foodItemRepository.findByCategoryId(categoryId)).thenReturn(foodItems);


        List<FoodItem> result = foodItemService.getFoodItemsByCategory(categoryId);


        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }
}
