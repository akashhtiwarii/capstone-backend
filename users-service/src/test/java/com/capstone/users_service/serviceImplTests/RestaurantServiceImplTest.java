package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveValidRestaurant() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);
        User owner = new User();
        owner.setRole(Role.OWNER);

        when(userRepository.findById(restaurantInDTO.getOwnerId())).thenReturn(owner);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(new Restaurant());

        String result = restaurantService.save(restaurantInDTO);

        assertEquals("Restaurant added successfully", result);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testSaveUserNotFound() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);

        when(userRepository.findById(restaurantInDTO.getOwnerId())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> restaurantService.save(restaurantInDTO));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    public void testSaveUserNotValid() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);
        User user = new User();
        user.setRole(Role.USER);

        when(userRepository.findById(restaurantInDTO.getOwnerId())).thenReturn(user);
        assertThrows(UserNotValidException.class, () -> restaurantService.save(restaurantInDTO));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    public void testFindAllNoRestaurantsFound() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findAll());
    }

    @Test
    public void testFindAllRestaurantsFound() {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurantList);

        List<Restaurant> result = restaurantService.findAll();

        assertEquals(1, result.size());
        verify(restaurantRepository, times(1)).findAll();
    }
}