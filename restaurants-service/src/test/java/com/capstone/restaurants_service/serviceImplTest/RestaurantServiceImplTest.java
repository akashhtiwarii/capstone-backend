package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private RestaurantConverters restaurantConverters;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRestaurant_UserNotFound() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(null));

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            restaurantService.save(restaurantInDTO);
        });
        assertEquals("Owner not in database", thrown.getMessage());
    }

    @Test
    void testSaveRestaurant_UserNotValid() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setRole(Role.USER);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));

        UserNotValidException thrown = assertThrows(UserNotValidException.class, () -> {
            restaurantService.save(restaurantInDTO);
        });
        assertEquals("You cannot add a restaurant", thrown.getMessage());
    }

    @Test
    void testSaveRestaurant_EmailAlreadyExists() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setEmail("test@example.com");

        when(userClient.getUserById(restaurantInDTO.getOwnerId())).thenReturn(ResponseEntity.ok(new UserOutDTO()));
        when(restaurantRepository.findByEmail(restaurantInDTO.getEmail())).thenReturn(new Restaurant());

        EmailAlreadyExistsException thrown = assertThrows(EmailAlreadyExistsException.class, () -> {
            restaurantService.save(restaurantInDTO);
        });
        assertEquals("Email ID already Exists", thrown.getMessage());
    }

    @Test
    void testSaveRestaurant_Success() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setEmail("test@example.com");
        restaurantInDTO.setOwnerId(1L);

        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setRole(Role.OWNER);

        Restaurant restaurant = new Restaurant();
        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail(restaurantInDTO.getEmail())).thenReturn(null);
        when(restaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        String response = restaurantService.save(restaurantInDTO);
        assertEquals("Restaurant added successfully", response);
    }

    @Test
    void testFindAll_NoRestaurantsFound() {
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

        RestaurantsNotFoundException thrown = assertThrows(RestaurantsNotFoundException.class, () -> {
            restaurantService.findAll();
        });
        assertEquals("No Restaurants in the database", thrown.getMessage());
    }

    @Test
    void testFindAll_Success() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));

        List<Restaurant> result = restaurantService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindById_RestaurantNotFound() {
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(null);

        RestaurantsNotFoundException thrown = assertThrows(RestaurantsNotFoundException.class, () -> {
            restaurantService.findById(restaurantId);
        });
        assertEquals("No Restaurants in the database", thrown.getMessage());
    }

    @Test
    void testFindById_Success() {
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantId)).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(restaurantId);
        assertNotNull(result);
    }
}
