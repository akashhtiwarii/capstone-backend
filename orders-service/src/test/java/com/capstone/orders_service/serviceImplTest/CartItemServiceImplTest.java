package com.capstone.orders_service.serviceImplTest;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;
import com.capstone.orders_service.dto.FoodItemOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UsersFeignClient usersFeignClient;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCartUserNotFound() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        when(usersFeignClient.getUserById(anyLong())).thenThrow(FeignException.NotFound.class);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });
        assertEquals(Constants.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testAddToCartFoodItemNotFound() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        when(usersFeignClient.getUserById(anyLong())).thenReturn(ResponseEntity.ok(new UserOutDTO()));
        when(restaurantFeignClient.getFoodItemById(anyLong())).thenThrow(FeignException.NotFound.class);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });
        assertEquals(Constants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteCartItemItemNotFound() {
        when(cartItemRepository.findById(anyLong())).thenReturn(null);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.deleteCartItem(1L);
        });
        assertEquals(Constants.CART_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteCartItemSuccess() {
        CartItem cartItem = new CartItem();
        when(cartItemRepository.findById(anyLong())).thenReturn(cartItem);
        String result = cartItemService.deleteCartItem(1L);
        assertEquals(Constants.CART_ITEM_DELETED_SUCCESSFULLY, result);
        verify(cartItemRepository).deleteById(1L);
    }

    @Test
    void testGetCartItemsUserHasNoCartItems() {
        when(cartItemRepository.findByUserId(anyLong())).thenReturn(new ArrayList<>());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.getCartItems(1L);
        });
        assertEquals(Constants.CART_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetCartItemsSuccess() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setUserId(1L);
        cartItem.setFoodId(1L);
        cartItem.setRestaurantId(1L);
        cartItem.setQuantity(2);
        cartItem.setPrice(100.0);

        when(cartItemRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(cartItem));
        when(restaurantFeignClient.getFoodItemById(anyLong())).thenReturn(ResponseEntity.ok(new FoodItemOutDTO()));
        when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(ResponseEntity.ok(new RestaurantOutDTO()));

        List<CartItemOutDTO> result = cartItemService.getCartItems(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getCartItemId());
    }

    @Test
    void testUpdateCartItemItemNotFound() {
        when(cartItemRepository.findById(anyLong())).thenReturn(null);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.updateCartItem(1L, 1);
        });
        assertEquals(Constants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testUpdateCartItem_Success() {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);

        when(cartItemRepository.findById(anyLong())).thenReturn(cartItem);
        String result = cartItemService.updateCartItem(1L, 1);
        assertEquals(Constants.CART_ITEM_UPDATED_SUCCESSFULLY, result);
        verify(cartItemRepository).save(cartItem);
        assertEquals(3, cartItem.getQuantity());
    }
}
