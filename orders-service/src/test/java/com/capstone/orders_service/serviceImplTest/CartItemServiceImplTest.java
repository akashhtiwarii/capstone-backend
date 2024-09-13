package com.capstone.orders_service.serviceImplTest;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.serviceImpl.CartItemServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CartItemServiceImplTest {

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
    void addToCart_userNotFound_throwsResourceNotFoundException() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setFoodId(1L);
        cartItem.setQuantity(1);

        when(usersFeignClient.getUserById(1L)).thenThrow(new FeignException.NotFound("User Not Found", null, null, null));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertEquals("User Not Found", thrown.getMessage());
    }

    @Test
    void addToCart_foodItemNotFound_throwsResourceNotFoundException() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setRestaurantId(1L);
        addToCartInDTO.setFoodId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setFoodId(1L);
        cartItem.setQuantity(1);

        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(new UserOutDTO()));
        when(restaurantFeignClient.getFoodItemById(1L)).thenThrow(new FeignException.NotFound("Food Item Not Found", null, null, null));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.addToCart(addToCartInDTO);
        });

        assertEquals("Food Item Not Found", thrown.getMessage());
    }

    @Test
    void deleteCartItem_itemNotFound_throwsResourceNotFoundException() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.deleteCartItem(1L);
        });

        assertEquals("Item Not Found", thrown.getMessage());
    }

    @Test
    void getCartItems_noItemsInCart_throwsResourceNotFoundException() {
        when(cartItemRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.getCartItems(1L);
        });

        assertEquals("No Items in Cart", thrown.getMessage());
    }

    @Test
    void updateCartItem_cartItemNotFound_throwsResourceNotFoundException() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.updateCartItem(1L, 1);
        });

        assertEquals("Cart Item is not present", thrown.getMessage());
    }
}