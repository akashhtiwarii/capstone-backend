package com.capstone.orders_service.controllerTest;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.controller.OrderController;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.service.CartItemService;
import com.capstone.orders_service.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrders() {
        List<OrderOutDTO> orders = Collections.singletonList(new OrderOutDTO());
        when(orderService.getOrders(anyLong(), anyLong())).thenReturn(orders);

        ResponseEntity<List<OrderOutDTO>> response = orderController.getOrders(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testAddOrder() {
        String responseMessage = "Order added successfully";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        when(orderService.addOrder(anyLong(), anyLong())).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.addOrder(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testGetMyCart() {
        List<CartItemOutDTO> cartItems = Collections.singletonList(new CartItemOutDTO());
        when(cartItemService.getCartItems(anyLong())).thenReturn(cartItems);

        ResponseEntity<List<CartItemOutDTO>> response = orderController.getMyCart(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    @Test
    void testAddToCart() {
        String responseMessage = "Item added to cart";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        when(cartItemService.addToCart(any(AddToCartInDTO.class))).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.addToCart(addToCartInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testUpdateOrder() {
        String responseMessage = "Order updated successfully";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        when(orderService.updateOrder(anyLong(), anyLong(), any(Status.class))).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.updateOrder(1L, 1L, Status.PENDING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testGetRestaurantOrderDetails() {
        List<RestaurantOrderDetailsOutDTO> details = Collections.singletonList(new RestaurantOrderDetailsOutDTO());
        when(orderService.getOrderDetails(anyLong())).thenReturn(details);

        ResponseEntity<List<RestaurantOrderDetailsOutDTO>> response = orderController.getRestaurantOrderDetails(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(details, response.getBody());
    }

    @Test
    void testGetUserOrders() {
        List<UserOrderDetailsOutDTO> orders = Collections.singletonList(new UserOrderDetailsOutDTO());
        when(orderService.getUserOrders(anyLong())).thenReturn(orders);

        ResponseEntity<List<UserOrderDetailsOutDTO>> response = orderController.getUserOrders(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testUpdateCartItem() {
        String responseMessage = "Cart item updated";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        when(cartItemService.updateCartItem(anyLong(), anyInt())).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.updateCartItem(1L, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testDeleteCartItem() {
        String responseMessage = "Cart item deleted";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        when(cartItemService.deleteCartItem(anyLong())).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.deleteCartItem(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testCancelOrder() {
        String responseMessage = "Order canceled";
        RequestSuccessOutDTO responseDTO = new RequestSuccessOutDTO(responseMessage);
        when(orderService.cancelOrder(anyLong())).thenReturn(responseMessage);

        ResponseEntity<RequestSuccessOutDTO> response = orderController.cancelOrder(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }
}
