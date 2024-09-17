package com.capstone.restaurants_service.serviceImplTest;


import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.ResourceNotFoundException;
import com.capstone.restaurants_service.exceptions.ResourceNotValidException;
import com.capstone.restaurants_service.serviceImpl.RestaurantServiceImpl;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
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

    public RestaurantServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserNotFound() {
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.save(restaurantInDTO, null)
        );
        assertEquals(Constants.USER_NOT_FOUND, thrown.getMessage());
    }

    @Test
    public void testSaveUserNotOwner() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> restaurantService.save(restaurantInDTO, null)
        );
        assertEquals(Constants.YOU_CANNOT_ADD_RESTAURANT, thrown.getMessage());
    }

    @Test
    public void testSaveRestaurantAlreadyExists() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(restaurantRepository.findByEmail(anyString())).thenReturn(new Restaurant());

        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setEmail("test@example.com");

        ResourceAlreadyExistsException thrown = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> restaurantService.save(restaurantInDTO, null)
        );
        assertEquals(Constants.EMAIL_ALREADY_EXISTS, thrown.getMessage());
    }

    @Test
    public void testSaveSuccess() throws IOException {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        user.setUserId(1L);
        user.setName("name");
        user.setEmail("email@gmail.com");
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(restaurantRepository.findByEmail(anyString())).thenReturn(null);
        when(restaurantRepository.save(any())).thenReturn(new Restaurant());
        when(image.getBytes()).thenReturn(new byte[]{0,1,2,3});

        RestaurantInDTO restaurantInDTO = new RestaurantInDTO();
        restaurantInDTO.setOwnerId(1L);
        restaurantInDTO.setEmail("test@example.com");
        restaurantInDTO.setPhone("9876654321");
        restaurantInDTO.setName("name");
        restaurantInDTO.setAddress("Address");

        String result = restaurantService.save(restaurantInDTO, image);
        assertEquals(Constants.RESTAURANT_ADDED_SUCCESSFULLY, result);
    }

    @Test
    public void testUpdateRestaurantUserNotFound() {
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(null));

        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO();
        updateRestaurantInDTO.setLoggedInOwnerId(1L);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.updateRestaurant(updateRestaurantInDTO, null)
        );
        assertEquals(Constants.USER_NOT_FOUND, thrown.getMessage());
    }

    @Test
    public void testUpdateRestaurantUserNotOwner() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.USER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO();
        updateRestaurantInDTO.setLoggedInOwnerId(1L);

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> restaurantService.updateRestaurant(updateRestaurantInDTO, null)
        );
        assertEquals(Constants.YOU_CANNOT_UPDATE_RESTAURANT, thrown.getMessage());
    }

    @Test
    public void testUpdateRestaurantRestaurantNotFound() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));
        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO();
        updateRestaurantInDTO.setLoggedInOwnerId(1L);
        updateRestaurantInDTO.setRestaurantId(1L);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.updateRestaurant(updateRestaurantInDTO, null)
        );
        assertEquals(Constants.RESTAURANT_DOES_NOT_EXISTS, thrown.getMessage());
    }

    @Test
    public void testUpdateRestaurantRestaurantNotOwner() {
        UserOutDTO user = new UserOutDTO();
        user.setRole(Role.OWNER);
        when(userClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(user));

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(2L);
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO();
        updateRestaurantInDTO.setLoggedInOwnerId(1L);
        updateRestaurantInDTO.setRestaurantId(1L);

        ResourceNotValidException thrown = assertThrows(
                ResourceNotValidException.class,
                () -> restaurantService.updateRestaurant(updateRestaurantInDTO, null)
        );
        assertEquals(Constants.YOU_CANNOT_UPDATE_RESTAURANT, thrown.getMessage());
    }

    @Test
    public void testFindAllNoRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.findAll()
        );
        assertEquals(Constants.RESTAURANT_DOES_NOT_EXISTS, thrown.getMessage());
    }

    @Test
    public void testFindAllSuccess() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(new Restaurant()));

        List<Restaurant> result = restaurantService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByOwnerIdNoRestaurants() {
        when(restaurantRepository.findByOwnerId(anyLong())).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.findByOwnerId(1L)
        );
        assertEquals(Constants.RESTAURANT_DOES_NOT_EXISTS, thrown.getMessage());
    }

    @Test
    public void testFindByOwnerIdSuccess() {
        when(restaurantRepository.findByOwnerId(anyLong())).thenReturn(Collections.singletonList(new Restaurant()));

        List<Restaurant> result = restaurantService.findByOwnerId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByIdRestaurantNotFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.findById(1L)
        );
        assertEquals(Constants.RESTAURANT_DOES_NOT_EXISTS, thrown.getMessage());
    }

    @Test
    public void testFindByIdSuccess() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(1L);
        assertEquals(restaurant, result);
    }
}

