package com.capstone.restaurants_service.serviceImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import java.util.ArrayList;
import java.util.List;

class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveRestaurantSuccessfully() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setEmail("test@example.com");

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.OWNER);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail(anyString())).thenReturn(null);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(new Restaurant());

        String result = restaurantService.save(restaurantInDTO);
        assertEquals("Restaurant added successfully", result);

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void save_shouldThrowExceptionWhenOwnerNotFound() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        assertThrows(UserNotFoundException.class, () -> restaurantService.save(restaurantInDTO));
    }

    @Test
    void save_shouldThrowExceptionWhenEmailAlreadyExists() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setEmail("test@example.com");

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.OWNER);

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail(anyString())).thenReturn(new Restaurant());

        assertThrows(EmailAlreadyExistsException.class, () -> restaurantService.save(restaurantInDTO));
    }

    @Test
    void save_shouldThrowExceptionWhenUserIsNotValid() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(1L);
        userOutDTO.setRole(Role.USER);  // User role, not allowed to add restaurant

        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> restaurantService.save(restaurantInDTO));
    }

    @Test
    void findAll_shouldReturnListOfRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());

        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.findAll();
        assertFalse(result.isEmpty());

        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void findAll_shouldThrowExceptionWhenNoRestaurantsFound() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findAll());
    }

    @Test
    void findByOwnerId_shouldReturnRestaurant() {
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO = new GetOwnerRestaurantsInDTO();
        getOwnerRestaurantsInDTO.setOwnerId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(1L);

        when(restaurantRepository.findByOwnerId(anyLong())).thenReturn(restaurant);

        Restaurant result = restaurantService.findByOwnerId(getOwnerRestaurantsInDTO);
        assertNotNull(result);

        verify(restaurantRepository, times(1)).findByOwnerId(anyLong());
    }

    @Test
    void findByOwnerId_shouldThrowExceptionWhenNoRestaurantFound() {
        GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO = new GetOwnerRestaurantsInDTO();
        getOwnerRestaurantsInDTO.setOwnerId(1L);

        when(restaurantRepository.findByOwnerId(anyLong())).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findByOwnerId(getOwnerRestaurantsInDTO));
    }

    @Test
    void findById_shouldReturnRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);

        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(1L);
        assertNotNull(result);

        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_shouldThrowExceptionWhenNoRestaurantFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findById(1L));
    }
}
