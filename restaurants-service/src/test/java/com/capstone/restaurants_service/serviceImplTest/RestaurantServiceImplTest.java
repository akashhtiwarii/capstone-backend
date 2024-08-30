package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.EmailAlreadyExistsException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.serviceImpl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRestaurant_Success() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address", null);
        UserOutDTO user = new UserOutDTO(1L, "test@gmail.com", "User Name", "9876543210", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));
        when(restaurantRepository.findByEmail("test@gmail.com")).thenReturn(null);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(new Restaurant());

        String result = restaurantService.save(restaurantInDTO);

        assertEquals("Restaurant added successfully", result);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testSaveRestaurant_EmailAlreadyExists() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address", null);
        UserOutDTO user = new UserOutDTO(1L, "test@gmail.com", "User Name", "9876543210", Role.OWNER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));
        when(restaurantRepository.findByEmail("test@gmail.com")).thenReturn(new Restaurant());

        assertThrows(EmailAlreadyExistsException.class, () -> restaurantService.save(restaurantInDTO));
    }

    @Test
    void testFindAllRestaurants_Success() {
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address", null);
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));

        List<Restaurant> result = restaurantService.findAll();

        assertEquals(Collections.singletonList(restaurant), result);
    }

    @Test
    void testFindById_Success() {
        Restaurant restaurant = new Restaurant(1L, 1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address", null);
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(1L);

        assertEquals(restaurant, result);
    }
}
