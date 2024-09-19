package com.capstone.orders_service.serviceImplTest;

import com.capstone.orders_service.Enum.Role;
import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.exceptions.RestaurantConflictException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.serviceImpl.CartItemServiceImpl;
import com.capstone.orders_service.utils.Constants;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UsersFeignClient usersFeignClient;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCartUserNotFound() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setQuantity(1);
        addToCartInDTO.setRestaurantId(1L);
        addToCartInDTO.setFoodId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertEquals(Constants.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testAddToCartFoodItemNotFound() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setFoodId(101L);
        addToCartInDTO.setRestaurantId(1001L);

        UserOutDTO user = new UserOutDTO();
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));
        when(restaurantFeignClient.getFoodItemById(101L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertEquals("Food Item Not Found", exception.getMessage());
    }

    @Test
    public void testAddToCartRestaurantConflict() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setFoodId(1L);
        addToCartInDTO.setRestaurantId(1001L);
        addToCartInDTO.setQuantity(1);

        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setPrice(10.0);
        foodItemOutDTO.setFoodId(1L);
        when(restaurantFeignClient.getFoodItemById(1L)).thenReturn(ResponseEntity.ok(foodItemOutDTO));

        List<FoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
        foodItemOutDTOS.add(foodItemOutDTO);
        when(restaurantFeignClient.getFoodItemsByRestaurant(1001L)).thenReturn(ResponseEntity.ok(foodItemOutDTOS));

        CartItem existingCartItem = new CartItem();
        existingCartItem.setRestaurantId(1002L);
        when(cartItemRepository.findByUserId(1L)).thenReturn(Arrays.asList(existingCartItem));

        RestaurantConflictException exception = assertThrows(RestaurantConflictException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertEquals(Constants.RESTAURANT_CONFLICT, exception.getMessage());
    }

    @Test
    public void testAddToCartSuccessfulAddition() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setFoodId(1L);
        addToCartInDTO.setRestaurantId(1001L);
        addToCartInDTO.setQuantity(1);

        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setPrice(10.0);
        foodItemOutDTO.setFoodId(1L);
        when(restaurantFeignClient.getFoodItemById(1L)).thenReturn(ResponseEntity.ok(foodItemOutDTO));

        List<FoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
        foodItemOutDTOS.add(foodItemOutDTO);
        when(restaurantFeignClient.getFoodItemsByRestaurant(1001L)).thenReturn(ResponseEntity.ok(foodItemOutDTOS));

        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);
        when(cartItemRepository.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        String result = cartItemService.addToCart(addToCartInDTO);

        assertEquals(Constants.CART_ITEM_ADDED_SUCCESSFULLY, result);
    }

    @Test
    public void testDeleteCartItemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.deleteCartItem(1L);
        });

        assertEquals(Constants.CART_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testDeleteCartItemSuccessfulDeletion() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);

        when(cartItemRepository.findById(1L)).thenReturn(cartItem);

        String result = cartItemService.deleteCartItem(1L);
        verify(cartItemRepository, times(1)).deleteById(1L);
        assertEquals(Constants.CART_ITEM_DELETED_SUCCESSFULLY, result);
    }

    @Test
    public void testGetCartItemsNoItemsFound() {
        when(cartItemRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.getCartItems(1L);
        });

        assertEquals(Constants.CART_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetCartItemsSuccess() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setUserId(1L);
        cartItem.setFoodId(101L);
        cartItem.setRestaurantId(1001L);
        cartItem.setQuantity(2);
        cartItem.setPrice(20.0);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);

        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setName("Food");
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();
        restaurantOutDTO.setName("Food Place");

        when(restaurantFeignClient.getFoodItemById(101L)).thenReturn(ResponseEntity.ok(foodItemOutDTO));
        when(restaurantFeignClient.getRestaurantById(1001L)).thenReturn(ResponseEntity.ok(restaurantOutDTO));

        CartItemsListOutDTO result = cartItemService.getCartItems(1L);
        assertEquals(1, result.getCartItemOutDTOList().size());
        assertEquals("Food", result.getCartItemOutDTOList().get(0).getFoodName());
        assertEquals("Food Place", result.getCartItemOutDTOList().get(0).getRestaurantName());
        assertEquals(2, result.getCartItemOutDTOList().get(0).getQuantity());
        assertEquals("20.0 x 2 = 40.0", result.getCartItemOutDTOList().get(0).getPriceQuantity());
        assertEquals(40.0, result.getTotalAmount());
    }

    @Test
    public void testUpdateCartItemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.updateCartItem(1L, 1);
        });

        assertEquals(Constants.CART_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testUpdateCartItemSuccess() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setQuantity(2);

        when(cartItemRepository.findById(1L)).thenReturn(cartItem);

        String result = cartItemService.updateCartItem(1L, 1);
        assertEquals(3, cartItem.getQuantity());
        assertEquals(Constants.CART_ITEM_UPDATED_SUCCESSFULLY, result);

        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    public void testAddToCartUserServiceUnavailable() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setQuantity(1);
        addToCartInDTO.setRestaurantId(1L);
        addToCartInDTO.setFoodId(1L);

        when(usersFeignClient.getUserById(1L)).thenThrow(FeignException.class);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertNotNull(exception);
    }

    @Test
    public void testAddToCartRestaurantServiceUnavailable() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setQuantity(1);
        addToCartInDTO.setRestaurantId(1L);
        addToCartInDTO.setFoodId(1L);

        UserOutDTO user = new UserOutDTO();
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        when(restaurantFeignClient.getFoodItemById(1L)).thenThrow(FeignException.class);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertNotNull(exception);
    }

    @Test
    public void testGetCartItemsFoodServiceUnavailable() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setUserId(1L);
        cartItem.setFoodId(101L);
        cartItem.setRestaurantId(1001L);

        when(cartItemRepository.findByUserId(1L)).thenReturn(Arrays.asList(cartItem));
        when(restaurantFeignClient.getFoodItemById(101L)).thenThrow(FeignException.class);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.getCartItems(1L);
        });

        assertNotNull(exception);
    }


}

