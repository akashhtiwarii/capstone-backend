package com.capstone.restaurants_service.serviceImplTest;

import com.capstone.restaurants_service.ENUM.Role;
import dto.InDTO.GetOwnerRestaurantsInDTO;
import dto.InDTO.RestaurantInDTO;
import dto.InDTO.UpdateRestaurantInDTO;
import dto.OutDTO.UserOutDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveRestaurantSuccess() throws IOException {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "1234567890", "Test Address");
        MultipartFile image = mock(MultipartFile.class);
        UserOutDTO userOutDTO = new UserOutDTO(1L,"test@gmail.com","test","9876543210", Role.OWNER);

        when(userClient.getUserById(restaurantInDTO.getOwnerId())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail(restaurantInDTO.getEmail())).thenReturn(null);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(new Restaurant());

        String result = restaurantService.save(restaurantInDTO, image);
        assertEquals("Restaurant added successfully", result);
    }

    @Test
    public void testSaveRestaurantUserNotFound() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "1234567890", "Test Address");

        when(userClient.getUserById(restaurantInDTO.getOwnerId())).thenReturn(ResponseEntity.of(Optional.empty()));

        assertThrows(UserNotFoundException.class, () -> restaurantService.save(restaurantInDTO, null));
    }

    @Test
    public void testSaveRestaurantInvalidUser() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "1234567890", "Test Address");
        UserOutDTO userOutDTO = new UserOutDTO(2L,"test@gmail.com","test","9876543210", Role.USER);

        when(userClient.getUserById(restaurantInDTO.getOwnerId())).thenReturn(ResponseEntity.ok(userOutDTO));

        assertThrows(UserNotValidException.class, () -> restaurantService.save(restaurantInDTO, null));
    }

    @Test
    public void testSaveRestaurantEmailAlreadyExists() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "1234567890", "Test Address");
        UserOutDTO userOutDTO = new UserOutDTO(1L,"test@gmail.com","test","9876543210", Role.OWNER);

        when(userClient.getUserById(restaurantInDTO.getOwnerId())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findByEmail(restaurantInDTO.getEmail())).thenReturn(new Restaurant());

        assertThrows(EmailAlreadyExistsException.class, () -> restaurantService.save(restaurantInDTO, null));
    }

    @Test
    public void testFindAllRestaurantsSuccess() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAllRestaurantsNotFound() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findAll());
    }

    @Test
    public void testUpdateRestaurantSuccess() throws IOException {
        UpdateRestaurantInDTO updateRestaurantInDTO = new UpdateRestaurantInDTO(1L, 1L, "Updated Restaurant", "updated@gmail.com", "0987654321", "Updated Address", null);
        UserOutDTO userOutDTO = new UserOutDTO(1L,"test@gmail.com","test","9876543210", Role.OWNER);
        Restaurant restaurant = new Restaurant(1L, 1L,"Old Restaurant", "old@gmail.com", "1234567890", "Address", null);

        when(userClient.getUserById(updateRestaurantInDTO.getLoggedInOwnerId())).thenReturn(ResponseEntity.ok(userOutDTO));
        when(restaurantRepository.findById(updateRestaurantInDTO.getRestaurantId())).thenReturn(restaurant);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        String result = restaurantService.updateRestaurant(updateRestaurantInDTO);
        assertEquals("Restaurant updated successfully", result);
    }

    @Test
    public void testFindRestaurantByIdSuccess() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(anyLong())).thenReturn(restaurant);

        Restaurant result = restaurantService.findById(1L);
        assertNotNull(result);
    }

    @Test
    public void testFindRestaurantByIdNotFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findById(1L));
    }

    @Test
    public void testFindRestaurantsByOwnerIdSuccess() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        GetOwnerRestaurantsInDTO dto = new GetOwnerRestaurantsInDTO(1L);

        when(restaurantRepository.findByOwnerId(dto.getOwnerId())).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.findByOwnerId(dto);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindRestaurantsByOwnerIdNotFound() {
        GetOwnerRestaurantsInDTO dto = new GetOwnerRestaurantsInDTO(1L);

        when(restaurantRepository.findByOwnerId(dto.getOwnerId())).thenReturn(new ArrayList<>());

        assertThrows(RestaurantsNotFoundException.class, () -> restaurantService.findByOwnerId(dto));
    }
}
