package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.EmailAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private MultipartFile image;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Success() throws IOException {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant A", "contact@restaurantA.com", "1234567890", "123 Main St", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "abc@gmail.com", "John", "9876543210", Role.OWNER); // Assuming Role.ADMIN is a valid role for adding a restaurant
        Restaurant restaurant = new Restaurant();

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail("contact@restaurantA.com")).thenReturn(null);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        String response = restaurantService.save(restaurantInDTO, image);

        assertEquals("Restaurant added successfully", response);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testSave_UserNotFound() throws IOException {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant A", "contact@restaurantA.com", "1234567890", "123 Main St", null);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(null));

        assertThrows(UserNotFoundException.class, () -> restaurantService.save(restaurantInDTO, image));
    }

    @Test
    public void testSave_EmailAlreadyExists() throws IOException {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant A", "contact@restaurantA.com", "1234567890", "123 Main St", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "abc@gmail.com", "John", "9876543210", Role.OWNER); // Assuming Role.ADMIN is a valid role for adding a restaurant

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail("contact@restaurantA.com")).thenReturn(new Restaurant());

        assertThrows(EmailAlreadyExistsException.class, () -> restaurantService.save(restaurantInDTO, image));
    }

    @Test
    public void testSave_FailToUploadImage() throws IOException {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant A", "contact@restaurantA.com", "1234567890", "123 Main St", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L, "abc@gmail.com", "John", "9876543210", Role.OWNER); // Assuming Role.ADMIN is a valid role for adding a restaurant

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail("contact@restaurantA.com")).thenReturn(null);
        when(image.getBytes()).thenThrow(new IOException("Failed to read image"));

        assertThrows(RuntimeException.class, () -> restaurantService.save(restaurantInDTO, image));
    }

    @Test
    public void testFindAll_Success() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());

        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAll_Empty() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findAll());
    }

    @Test
    public void testFindByOwnerId_Success() {
        GetOwnerRestaurantsInDTO dto = new GetOwnerRestaurantsInDTO(1L);
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());

        when(restaurantRepository.findByOwnerId(1L)).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.findByOwnerId(dto);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByOwnerId_Empty() {
        GetOwnerRestaurantsInDTO dto = new GetOwnerRestaurantsInDTO(1L);

        when(restaurantRepository.findByOwnerId(1L)).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findByOwnerId(dto));
    }

    @Test
    public void testFindById_Success() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(1L);
        assertNotNull(result);
    }

    @Test
    public void testFindById_NotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findById(1L));
    }
}
