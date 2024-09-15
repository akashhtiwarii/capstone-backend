package com.capstone.orders_service.serviceImplTest;

import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.exceptions.RestaurantConflictException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.serviceImpl.CartItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartItemServiceImplTest {

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UsersFeignClient usersFeignClient;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToCart_userNotFound() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        when(usersFeignClient.getUserById(anyLong())).thenThrow(new ResourceNotFoundException("User Not Found"));

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.addToCart(addToCartInDTO));
    }

    @Test
    void deleteCartItem_itemFound() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        when(cartItemRepository.findById(1L)).thenReturn(cartItem);

        String result = cartItemService.deleteCartItem(1L);

        assertEquals("Cart Item Deleted", result);
        verify(cartItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCartItem_itemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.deleteCartItem(1L));
    }

    @Test
    void getCartItems_successful() {
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(1L);
        cartItem.setFoodId(1L);
        cartItem.setRestaurantId(1L);
        cartItems.add(cartItem);

        when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        when(restaurantFeignClient.getFoodItemById(anyLong())).thenReturn(ResponseEntity.ok(new FoodItemOutDTO()));
        when(restaurantFeignClient.getRestaurantById(anyLong())).thenReturn(ResponseEntity.ok(new RestaurantOutDTO()));

        List<CartItemOutDTO> result = cartItemService.getCartItems(1L);

        assertFalse(result.isEmpty());
        verify(cartItemRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getCartItems_noItems() {
        when(cartItemRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.getCartItems(1L));
    }

    @Test
    void updateCartItem_successful() {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        when(cartItemRepository.findById(1L)).thenReturn(cartItem);

        String result = cartItemService.updateCartItem(1L, 1);

        assertEquals("Cart Updated Successfully", result);
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void updateCartItem_itemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> cartItemService.updateCartItem(1L, 1));
    }
}
