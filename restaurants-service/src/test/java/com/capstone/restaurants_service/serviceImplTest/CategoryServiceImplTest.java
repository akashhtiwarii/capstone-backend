package com.capstone.restaurants_service.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.ResourceNotFoundException;
import com.capstone.restaurants_service.exceptions.ResourceNotValidException;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.CategoryInDTO;
import com.capstone.restaurants_service.dto.UpdateCategoryDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.CategoryRepository;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCategoryUserNotFound() {
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.addCategory(new CategoryInDTO())
        );
        assertEquals(Constants.USER_NOT_FOUND, thrown.getMessage());
    }

    @Test
    public void testAddCategoryUserNotOwner() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> categoryService.addCategory(new CategoryInDTO())
        );
        assertEquals(Constants.YOU_CANNOT_ADD_A_CATEGORY, thrown.getMessage());
    }

    @Test
    public void testAddCategorySuccess() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(categoryRepository.findByNameAndRestaurantId(any(), anyLong())).thenReturn(null);

        Category category = new Category();
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryInDTO dto = new CategoryInDTO();
        dto.setName("Test Category");
        dto.setRestaurantId(1L);
        dto.setUserId(1L);

        String result = categoryService.addCategory(dto);
        assertEquals(Constants.CATEGORY_ADDED_SUCCESSFULLY, result);
    }

    @Test
    public void testUpdateCategorySuccess() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        Category existingCategory = new Category();
        existingCategory.setName("OLD_NAME");
        existingCategory.setRestaurantId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(existingCategory);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        when(categoryRepository.findByNameAndRestaurantId(any(), anyLong())).thenReturn(null);

        Category category = new Category();
        when(categoryRepository.save(any())).thenReturn(category);

        UpdateCategoryDTO dto = new UpdateCategoryDTO();
        dto.setName("NEW_NAME");
        dto.setUserId(1L);

        String result = categoryService.updateCategory(1L, dto);
        assertEquals(Constants.CATEGORY_UPDATED_SUCCESSFULLY, result);
    }

    @Test
    public void testDeleteCategoryUserNotFound() {
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(1L, 1L)
        );
        assertEquals(Constants.USER_NOT_FOUND, thrown.getMessage());
    }

    @Test
    public void testDeleteCategoryUserNotOwner() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> categoryService.deleteCategory(1L, 1L)
        );
        assertEquals(Constants.YOU_CANNOT_DELETE_CATEGORY, thrown.getMessage());
    }

    @Test
    public void testDeleteCategoryNotOwnerOfRestaurant() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        Category category = new Category();
        category.setRestaurantId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(2L);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> categoryService.deleteCategory(1L, 1L)
        );
        assertEquals(Constants.YOU_CANNOT_DELETE_CATEGORY, thrown.getMessage());
    }

    @Test
    public void testDeleteCategorySuccess() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        Category category = new Category();
        category.setRestaurantId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);
        when(categoryRepository.findById(anyLong())).thenReturn(category);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);
        doNothing().when(categoryRepository).deleteById(anyLong());

        String result = categoryService.deleteCategory(1L, 1L);
        assertEquals(Constants.CATEGORY_DELETED, result);
    }
}