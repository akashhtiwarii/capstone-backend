package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.dto.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.dto.InDTO.UpdateCategoryDTO;
import com.capstone.restaurants_service.exceptions.*;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.serviceImpl.CategoryServiceImpl;
import com.capstone.restaurants_service.dto.OutDTO.UserOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

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
    void addCategorySuccess() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        restaurant.setRestaurantId(1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(categoryRepository.findByNameAndRestaurantId("DESSERTS", 1L)).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        String result = categoryService.addCategory(categoryInDTO);
        assertEquals("Category Added Successfully", result);
    }

    @Test
    void addCategoryUserNotFound() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", null);
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(null));

        assertThrows(UserNotFoundException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void addCategoryInvalidUserRole() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.USER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void addCategoryRestaurantNotFound() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void updateCategorySuccess() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO(1L, "New Desserts");
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);
        Category category = new Category();
        category.setName("DESSERTS");
        category.setRestaurantId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        restaurant.setRestaurantId(1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(categoryRepository.findByNameAndRestaurantId("NEW DESSERTS", 1L)).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        String result = categoryService.updateCategory(1L, updateCategoryDTO);
        assertEquals("Category updated successfully", result);
    }

    @Test
    void updateCategoryCategoryNotFound() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO(1L, "New Desserts");
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1L, updateCategoryDTO));
    }

    @Test
    void deleteCategorySuccess() {
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);
        Category category = new Category();
        category.setRestaurantId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        restaurant.setRestaurantId(1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);

        String result = categoryService.deleteCategory(1L,1L);
        assertEquals("Category Deleted Successfully", result);
    }

    @Test
    void deleteCategoryCategoryNotFound() {
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "Name", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L,1L));
    }
}
