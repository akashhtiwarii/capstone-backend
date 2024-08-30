package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.CategoryInDTO;
import com.capstone.restaurants_service.InDTO.DeleteCategoryInDTO;
import com.capstone.restaurants_service.InDTO.GetAllCategoriesInDTO;
import com.capstone.restaurants_service.InDTO.UpdateCategoryDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.CategoryConverters;
import com.capstone.restaurants_service.entity.Category;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.CategoryAlreadyExistException;
import com.capstone.restaurants_service.exceptions.CategoryNotFoundException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategory_Success() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", new byte[]{});
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "email@example.com", "1234567890", "Address", new byte[]{});

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(categoryRepository.findByName("DESSERTS")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        String response = categoryService.addCategory(categoryInDTO);
        assertEquals("Category Added Successfully", response);
    }

    @Test
    void testAddCategory_UserNotFound() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", new byte[]{});

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.notFound().build());

        assertThrows(UserNotFoundException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void testAddCategory_UserNotValid() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", new byte[]{});
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.USER);
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "email@example.com", "1234567890", "Address", new byte[]{});

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);

        assertThrows(UserNotValidException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void testAddCategory_RestaurantNotFound() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", new byte[]{});
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void testAddCategory_CategoryAlreadyExists() {
        CategoryInDTO categoryInDTO = new CategoryInDTO(1L, 1L, "Desserts", new byte[]{});
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "email@example.com", "1234567890", "Address", new byte[]{});
        Category existingCategory = new Category();

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(categoryRepository.findByName("DESSERTS")).thenReturn(existingCategory);

        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.addCategory(categoryInDTO));
    }

    @Test
    void testGetAllCategoriesOfRestaurant_Success() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "email@example.com", "1234567890", "Address", new byte[]{});
        Category category = new Category();
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByOwnerId(1L)).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(1L)).thenReturn(Arrays.asList(category));

        List<Category> categories = categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO);
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    void testGetAllCategoriesOfRestaurant_UserNotFound() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO(1L, 1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.notFound().build());

        assertThrows(UserNotFoundException.class, () -> categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO));
    }

    @Test
    void testGetAllCategoriesOfRestaurant_UserNotValid() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.USER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO));
    }

    @Test
    void testGetAllCategoriesOfRestaurant_RestaurantNotFound() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByOwnerId(1L)).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO));
    }

    @Test
    void testGetAllCategoriesOfRestaurant_NoCategoriesFound() {
        GetAllCategoriesInDTO getAllCategoriesInDTO = new GetAllCategoriesInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant", "email@example.com", "1234567890", "Address", new byte[]{});

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByOwnerId(1L)).thenReturn(restaurant);
        when(categoryRepository.findByRestaurantId(1L)).thenReturn(Collections.emptyList());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getAllCategoriesOfRestaurant(getAllCategoriesInDTO));
    }

    @Test
    void testUpdateCategory_UserNotFound() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO(1L, "NewName");

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.notFound().build());

        assertThrows(UserNotFoundException.class, () -> categoryService.updateCategory(1L, updateCategoryDTO));
    }

    @Test
    void testUpdateCategory_UserNotValid() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO(1L, "NewName");
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.USER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> categoryService.updateCategory(1L, updateCategoryDTO));
    }

    @Test
    void testUpdateCategory_CategoryNotFound() {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO(1L, "NewName");
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1L, updateCategoryDTO));
    }

    @Test
    void testDeleteCategory_UserNotFound() {
        DeleteCategoryInDTO deleteCategoryInDTO = new DeleteCategoryInDTO(1L, 1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.notFound().build());

        assertThrows(UserNotFoundException.class, () -> categoryService.deleteCategory(deleteCategoryInDTO));
    }

    @Test
    void testDeleteCategory_UserNotValid() {
        DeleteCategoryInDTO deleteCategoryInDTO = new DeleteCategoryInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.USER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> categoryService.deleteCategory(deleteCategoryInDTO));
    }

    @Test
    void testDeleteCategory_CategoryNotFound() {
        DeleteCategoryInDTO deleteCategoryInDTO = new DeleteCategoryInDTO(1L, 1L);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "email@example.com", "User", "1234567890", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(deleteCategoryInDTO));
    }
}

