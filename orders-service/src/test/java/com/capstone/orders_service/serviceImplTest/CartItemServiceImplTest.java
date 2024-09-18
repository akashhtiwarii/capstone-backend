package com.capstone.orders_service.serviceImplTest;

import com.capstone.orders_service.Enum.Role;
import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.serviceImpl.CartItemServiceImpl;
import com.capstone.orders_service.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
        MockitoAnnotations.initMocks(this);
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

        assertEquals("Food Not Found", exception.getMessage());
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
        user.setEmail("email@gmail.com");
        user.setPhone("1234567890");
        user.setName("name");
        user.setRole(Role.USER);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        FoodItemOutDTO foodItemOutDTO = new FoodItemOutDTO();
        foodItemOutDTO.setPrice(10.0);
        foodItemOutDTO.setFoodId(1L);
        foodItemOutDTO.setImage(new byte[0]);
        foodItemOutDTO.setCategoryId(1L);
        foodItemOutDTO.setDescription("Description");
        foodItemOutDTO.setName("name");
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

        List<CartItemOutDTO> result = cartItemService.getCartItems(1L);
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getFoodName());
        assertEquals("Food Place", result.get(0).getRestaurantName());
        assertEquals(2, result.get(0).getQuantity());
        assertEquals(20.0, result.get(0).getPrice());
    }

    @Test
    public void testUpdateCartItemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartItemService.updateCartItem(1L, 1);
        });

        assertEquals(Constants.FOOD_ITEM_NOT_FOUND, exception.getMessage());
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
}
