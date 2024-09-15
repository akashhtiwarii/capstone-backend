package com.capstone.orders_service.serviceImplTest;


import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.converters.OrderConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.entity.Order;
import com.capstone.orders_service.entity.OrderDetail;
import com.capstone.orders_service.exceptions.InsufficientAmountException;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.exceptions.ResourceNotValidException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.repository.OrderDetailRepository;
import com.capstone.orders_service.repository.OrderRepository;
import com.capstone.orders_service.serviceImpl.OrderServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private RestaurantFeignClient restaurantFeignClient;

    @Mock
    private UsersFeignClient usersFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getOrders_UserNotOwner() {
        long loggedInUserId = 1L;
        long restaurantId = 1L;
        UserOutDTO user = new UserOutDTO();
        user.setUserId(loggedInUserId);
        RestaurantOutDTO restaurant = new RestaurantOutDTO();
        restaurant.setOwnerId(2L);
        when(usersFeignClient.getUserById(loggedInUserId)).thenReturn(ResponseEntity.ok(user));
        when(restaurantFeignClient.getRestaurantById(restaurantId)).thenReturn(ResponseEntity.ok(restaurant));
        assertThrows(ResourceNotValidException.class, () -> orderService.getOrders(loggedInUserId, restaurantId));
    }

    @Test
    void deleteOrder_Success() {
        long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(order);
        List<OrderDetail> orderDetails = Collections.singletonList(new OrderDetail());
        when(orderDetailRepository.findByOrderId(orderId)).thenReturn(orderDetails);
        String result = orderService.deleteOrder(orderId);
        assertEquals("Order Deleted Successfully", result);
    }

    @Test
    void deleteOrder_NotFound() {
        long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(orderId));
    }

    @Test
    void updateOrder_Success() {
        long orderId = 1L;
        long userId = 1L;
        Order order = new Order();
        order.setRestaurantId(1L);
        when(orderRepository.findById(orderId)).thenReturn(order);
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(new RestaurantOutDTO()));
        String result = orderService.updateOrder(userId, orderId, Status.PENDING);
        assertEquals("Order Updated Successfully", result);
    }

    @Test
    void updateOrder_OrderNotFound() {
        long orderId = 1L;
        long userId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(userId, orderId, Status.PENDING));
    }

    @Test
    void getUserOrders_Success() {
        long userId = 1L;
        UserOutDTO user = new UserOutDTO();
        user.setUserId(userId);
        Order order = new Order();
        order.setRestaurantId(1L);
        List<Order> orders = Arrays.asList(order);
        when(usersFeignClient.getUserById(userId)).thenReturn(ResponseEntity.ok(user));
        when(orderRepository.findByUserId(userId)).thenReturn(orders);
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(new RestaurantOutDTO()));
        List<UserOrderDetailsOutDTO> result = orderService.getUserOrders(userId);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getUserOrders_NoOrders() {
        long userId = 1L;
        UserOutDTO user = new UserOutDTO();
        user.setUserId(userId);
        when(usersFeignClient.getUserById(userId)).thenReturn(ResponseEntity.ok(user));
        when(orderRepository.findByUserId(userId)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getUserOrders(userId));
    }

    @Test
    void cancelOrder_Success() {
        long orderId = 1L;
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now().minusSeconds(10));
        when(orderRepository.findById(orderId)).thenReturn(order);
        String result = orderService.cancelOrder(orderId);
        assertEquals("Order Cancelled Successfully", result);
    }

    @Test
    void cancelOrder_Timeout() {
        long orderId = 1L;
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now().minusSeconds(100));
        when(orderRepository.findById(orderId)).thenReturn(order);
        assertThrows(ResourceNotValidException.class, () -> orderService.cancelOrder(orderId));
    }
}
