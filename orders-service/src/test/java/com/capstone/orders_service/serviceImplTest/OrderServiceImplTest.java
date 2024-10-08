package com.capstone.orders_service.serviceImplTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.Enum.Status;
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
import com.capstone.orders_service.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

class OrderServiceImplTest {

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

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersSuccess() {
        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        RestaurantOutDTO restaurant = new RestaurantOutDTO();
        restaurant.setOwnerId(1L);
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(restaurant));

        Order order = new Order();
        order.setRestaurantId(1L);
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByRestaurantId(1L)).thenReturn(orders);

        List<OrderOutDTO> result = orderService.getOrders(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetOrdersUserNotOwnerThrowsException() {
        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        RestaurantOutDTO restaurant = new RestaurantOutDTO();
        restaurant.setOwnerId(2L);
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(restaurant));

        ResourceNotValidException exception = assertThrows(ResourceNotValidException.class,
                () -> orderService.getOrders(1L, 1L));
        assertEquals(Constants.USER_NOT_VALID, exception.getMessage());
    }

    @Test
    void testAddOrderSuccess() {
        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        AddressOutDTO address = new AddressOutDTO();
        address.setAddressId(1L);
        when(usersFeignClient.getAddressById(1L)).thenReturn(ResponseEntity.ok(address));

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setPrice(10.0);
        cartItem.setQuantity(2);
        cartItem.setUserId(1L);
        cartItem.setFoodId(1L);
        cartItems.add(cartItem);
        when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);

        WalletOutDTO wallet = new WalletOutDTO();
        wallet.setAmount(100.0);
        wallet.setUserId(1L);
        when(usersFeignClient.getUserWallet(1L)).thenReturn(ResponseEntity.ok(wallet));

        when(usersFeignClient.updateUserWallet(anyLong(), anyDouble())).thenReturn(ResponseEntity.ok("Wallet updated"));

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(new OrderDetail());
        doNothing().when(cartItemRepository).delete(any(CartItem.class));

        String result = orderService.addOrder(1L, 1L);

        assertEquals(Constants.ORDER_ADDED_SUCCESSFULLY, result);
    }

    @Test
    void testAddOrderInsufficientFundsThrowsException() {
        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        AddressOutDTO address = new AddressOutDTO();
        address.setAddressId(1L);
        when(usersFeignClient.getAddressById(1L)).thenReturn(ResponseEntity.ok(address));

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setPrice(100.0);
        cartItem.setQuantity(2);
        cartItems.add(cartItem);
        when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);

        WalletOutDTO wallet = new WalletOutDTO();
        wallet.setAmount(50.0);
        when(usersFeignClient.getUserWallet(1L)).thenReturn(ResponseEntity.ok(wallet));

        InsufficientAmountException exception = assertThrows(InsufficientAmountException.class,
                () -> orderService.addOrder(1L, 1L));

        assertEquals(Constants.INSUFFICIENT_WALLET_AMOUNT, exception.getMessage());
    }

    @Test
    void testDeleteOrderSuccess() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetails.add(orderDetail);
        when(orderDetailRepository.findByOrderId(1L)).thenReturn(orderDetails);

        String result = orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(order);
        verify(orderDetailRepository, times(1)).delete(orderDetail);

        assertEquals(Constants.ORDER_DELETED_SUCCESSFULLY, result);
    }

    @Test
    void testDeleteOrderOrderNotFoundThrowsException() {
        when(orderRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderService.deleteOrder(1L));

        assertEquals(Constants.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCancelOrderSuccess() {
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now().minusSeconds(20));
        order.setUserId(1L);
        order.setPrice(100.0);
        order.setStatus(Status.ONGOING);

        when(orderRepository.findById(1L)).thenReturn(order);

        WalletOutDTO wallet = new WalletOutDTO();
        wallet.setAmount(200.0);
        when(usersFeignClient.getUserWallet(order.getUserId())).thenReturn(ResponseEntity.ok(wallet));

        when(usersFeignClient.updateUserWallet(order.getUserId(), wallet.getAmount() + order.getPrice())).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Wallet Update"));

        String result = orderService.cancelOrder(1L);

        assertEquals(Constants.ORDER_CANCELLED_SUCCESSFULLY, result);
        assertEquals(Status.CANCELLED, order.getStatus());

        verify(orderRepository).save(order);
        verify(usersFeignClient).getUserWallet(order.getUserId());
        verify(usersFeignClient).updateUserWallet(order.getUserId(), wallet.getAmount() + order.getPrice());
    }


    @Test
    void testCancelOrderTimeExceededThrowsException() {
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now().minusSeconds(40));
        when(orderRepository.findById(1L)).thenReturn(order);

        ResourceNotValidException exception = assertThrows(ResourceNotValidException.class,
                () -> orderService.cancelOrder(1L));

        assertTrue(exception.getMessage().contains(Constants.CANNOT_CANCEL_ORDER));
    }

    @Test
    void testGetOrderDetailsSuccess() {
        long restaurantId = 1L;

        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(1L);
        order.setAddressId(1L);
        order.setPrice(100.0);
        order.setOrderTime(LocalDateTime.now());
        order.setRestaurantId(1L);
        order.setStatus(Status.PENDING);

        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findByRestaurantId(restaurantId)).thenReturn(orders);

        UserOutDTO user = new UserOutDTO();
        user.setName("name");
        user.setUserId(1L);
        when(usersFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFoodId(1L);
        orderDetail.setQuantity(2);
        orderDetail.setPrice(10.0);
        orderDetail.setOrderDetailId(1L);
        orderDetail.setOrderId(1L);

        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        when(orderDetailRepository.findByOrderId(1L)).thenReturn(orderDetails);

        FoodItemOutDTO foodItem = new FoodItemOutDTO();
        foodItem.setName("Food2");
        foodItem.setFoodId(1L);
        foodItem.setImage(new byte[0]);
        foodItem.setDescription("Description");
        foodItem.setCategoryId(1L);
        foodItem.setPrice(100.0);
        when(restaurantFeignClient.getFoodItemById(1L)).thenReturn(ResponseEntity.ok(foodItem));

        AddressOutDTO address = new AddressOutDTO();
        address.setAddressId(1L);
        address.setUserId(1L);
        address.setAddress("address");
        address.setState("state");
        address.setCity("city");
        address.setPincode("123456");
        when(usersFeignClient.getAddressById(1L)).thenReturn(ResponseEntity.ok(address));

        List<RestaurantOrderDetailsOutDTO> result = orderService.getOrderDetails(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());

        RestaurantOrderDetailsOutDTO orderDetailsOutDTO = result.get(0);
        assertEquals(1L, orderDetailsOutDTO.getOrderId());
        assertEquals("name", orderDetailsOutDTO.getUserName());
        assertEquals("address, city, state, 123456", orderDetailsOutDTO.getAddress());
        assertEquals(Status.PENDING, orderDetailsOutDTO.getStatus());

        List<OrderDetailOutDTO> orderDetailOutDTOS = orderDetailsOutDTO.getOrderDetailOutDTOS();
        assertNotNull(orderDetailOutDTOS);
        assertEquals(1, orderDetailOutDTOS.size());

        OrderDetailOutDTO orderDetailOutDTO = orderDetailOutDTOS.get(0);
        assertEquals("Food2", orderDetailOutDTO.getFoodName());
        assertEquals(2, orderDetailOutDTO.getQuantity());
        assertEquals(20.0, orderDetailOutDTO.getPrice(), 0.01);
    }


    @Test
    void testGetOrderDetailsNoOrders() {
        long restaurantId = 1L;

        when(orderRepository.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderDetails(restaurantId);
        });

        assertEquals(Constants.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetUserOrdersSuccess() {
        long userId = 1L;


        UserOutDTO user = new UserOutDTO();
        user.setUserId(1L);
        when(usersFeignClient.getUserById(userId)).thenReturn(ResponseEntity.ok(user));


        Order order = new Order();
        order.setOrderId(1L);
        order.setRestaurantId(1L);
        order.setStatus(Status.PENDING);

        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findByUserId(userId)).thenReturn(orders);


        RestaurantOutDTO restaurant = new RestaurantOutDTO();
        restaurant.setName("Food Place");
        restaurant.setEmail("contact@pizzaplace.com");
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(restaurant));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFoodId(1L);
        orderDetail.setQuantity(2);
        orderDetail.setPrice(5.0);

        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        when(orderDetailRepository.findByOrderId(1L)).thenReturn(orderDetails);

        FoodItemOutDTO foodItem = new FoodItemOutDTO();
        foodItem.setFoodId(1L);
        foodItem.setName("Food");
        foodItem.setPrice(5.0);
        when(restaurantFeignClient.getFoodItemById(1L)).thenReturn(ResponseEntity.ok(foodItem));

        List<UserOrderDetailsOutDTO> result = orderService.getUserOrders(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Food Place", result.get(0).getRestaurantName());
        assertEquals("contact@pizzaplace.com", result.get(0).getRestaurantEmail());
    }

    @Test
    void testGetUserOrdersNoOrders() {
        long userId = 1L;

        when(usersFeignClient.getUserById(userId)).thenReturn(ResponseEntity.ok(new UserOutDTO()));
        when(orderRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getUserOrders(userId);
        });

        assertEquals(Constants.ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testUpdateOrderOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrder(1L, 1L, Status.PENDING));

        assertEquals(Constants.ORDER_NOT_FOUND, exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateOrderSuccess() {
        Order order = new Order();
        order.setRestaurantId(1L);
        when(orderRepository.findById(1L)).thenReturn(order);

        RestaurantOutDTO restaurant = new RestaurantOutDTO();
        restaurant.setRestaurantId(1L);
        when(restaurantFeignClient.getRestaurantById(1L)).thenReturn(ResponseEntity.ok(restaurant));

        String result = orderService.updateOrder(1L, 1L, Status.PENDING);

        assertEquals(Constants.ORDER_UPDATED_SUCCESSFULLY, result);
        verify(orderRepository, times(1)).findById(1L);
        verify(restaurantFeignClient, times(1)).getRestaurantById(1L);
        verify(orderRepository, times(1)).save(order);
        assertEquals(Status.PENDING, order.getStatus());
    }



}
