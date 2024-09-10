package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.converters.OrderConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.entity.Order;
import com.capstone.orders_service.entity.OrderDetail;
import com.capstone.orders_service.exceptions.*;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.repository.OrderDetailRepository;
import com.capstone.orders_service.repository.OrderRepository;
import com.capstone.orders_service.service.OrderService;
import feign.FeignException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    @Autowired
    private UsersFeignClient usersFeignClient;

    /**
     * @param loggedInUserId
     * @param restaurantId
     * @return list of orders
     */
    @Override
    public List<OrderOutDTO> getOrders(long loggedInUserId, long restaurantId) {
        try {
            UserOutDTO user = usersFeignClient.getUserById(loggedInUserId).getBody();
            RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(restaurantId).getBody();
            if (restaurant.getOwnerId() != user.getUserId()) {
                throw new ResourceNotValidException("Resource Not Valid");
            }
            List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
            if (orders.isEmpty()) {
                throw new ResourceNotFoundException("No orders Present");
            }
            List<OrderOutDTO> orderOutDTOS = new ArrayList<>();
            for (Order order : orders) {
                orderOutDTOS.add(OrderConverter.orderToOrderOutDTO(order));
            }
            return orderOutDTOS;
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    /**
     * @param userId
     * @return String message
     */
    @Override
    public String addOrder(long userId) {
        UserOutDTO user;
        AddressOutDTO addressOutDTO;
        WalletOutDTO wallet;
        try {
            user = usersFeignClient.getUserById(userId).getBody();
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User Not Found");
        }
        try {
            addressOutDTO = usersFeignClient.getAddressById(userId).getBody();
        } catch (FeignException.NotFound e) {
            throw new AddressNotFoundException("No Address Added");
        }
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new CartItemDoesNotExistsException("No Items in your Cart");
        }
        double price = 0.0;
        for (CartItem cartItem : cartItems) {
            price += cartItem.getPrice() * cartItem.getQuantity();
        }
        try {
            wallet = usersFeignClient.getUserWallet(userId).getBody();
            if (wallet.getAmount() < price) {
                throw new InsufficientAmountException("You do not have enough money in your wallet");
            }
            double updatedAmount = wallet.getAmount() - price;
            String message = usersFeignClient.updateUserWallet(userId, updatedAmount).getBody();
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User not found");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(cartItems.get(0).getRestaurantId());
        order.setAddressId(addressOutDTO.getAddressId());
        order.setOrderTime(LocalDateTime.now());
        order.setPrice(price);
        order.setStatus(Status.PENDING);
        try {
            Order savedOrder = orderRepository.save(order);
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(savedOrder.getOrderId());
                orderDetail.setPrice(cartItem.getPrice());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setFoodId(cartItem.getFoodId());
                orderDetailRepository.save(orderDetail);
                cartItemRepository.delete(cartItem);
            }
            return "Order Added Successfully";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred" + e.getMessage());
        }
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public String deleteOrder(long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Specified order not present");
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailRepository.delete(orderDetail);
        }
        orderRepository.delete(order);
        return "Order Deleted Successfully";
    }

    /**
     * @param orderId
     * @param userId
     * @return
     */
    @Override
    public String updateOrder(long userId, long orderId, Status status) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order Not Found");
        }
        try {
            List<RestaurantOutDTO> restaurants = restaurantFeignClient.getRestaurantByOwnerId(userId).getBody();
            long restaurantId = order.getRestaurantId();
            boolean restaurantExists = restaurants.stream()
                    .anyMatch(restaurant -> restaurant.getRestaurantId() == restaurantId);
            if (!restaurantExists) {
                throw new ResourceNotValidException("Invalid Request");
            }
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Restaurant Not Found");
        }
        order.setStatus(status);
        orderRepository.save(order);
        return "Order Updated Successfully";
    }

    /**
     * @param restaurantId
     * @return
     */
    @Override
    public List<OrderDetailsOutDTO> getOrderDetails(long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        String address = "";
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No Orders Present");
        }
        List<OrderDetailsOutDTO> orderDetailsOutDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDetailsOutDTO orderDetailsOutDTO = new OrderDetailsOutDTO();
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());
            try {
                address = usersFeignClient.getAddressById(order.getUserId()).getBody().toString();
            } catch (FeignException.NotFound e) {
                throw new AddressNotFoundException("No Address Present for an Order");
            }
            orderDetailsOutDTO.setUserId(order.getUserId());
            orderDetailsOutDTO.setOrderDetailList(orderDetails);
            orderDetailsOutDTO.setAddress(address);
            orderDetailsOutDTO.setStatus(order.getStatus());
            orderDetailsOutDTOS.add(orderDetailsOutDTO);
        }
        return orderDetailsOutDTOS;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<UserOrderDetailsOutDTO> getUserOrders(long userId) {
        try {
            UserOutDTO user = usersFeignClient.getUserById(userId).getBody();
            List<Order> orders = orderRepository.findByUserId(user.getUserId());
            if (orders.isEmpty()) {
                throw new OrderNotFoundException("You Do not have any orders");
            }
            List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = new ArrayList<>();
            for (Order order : orders) {
                try {
                    String restaurantName = restaurantFeignClient.getRestaurantById(order.getRestaurantId()).getBody().getName();
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());
                    UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO();
                    userOrderDetailsOutDTO.setRestaurantName(restaurantName);
                    userOrderDetailsOutDTO.setStatus(order.getStatus());
                    List<UserFoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
                    for (OrderDetail orderDetail : orderDetails) {
                        FoodItemOutDTO foodItem = restaurantFeignClient.getFoodItemById(orderDetail.getFoodId()).getBody();
                        UserFoodItemOutDTO userFoodItemOutDTO = new UserFoodItemOutDTO();
                        userFoodItemOutDTO.setFoodId(foodItem.getFoodId());
                        userFoodItemOutDTO.setName(foodItem.getName());
                        userFoodItemOutDTO.setQuantity(orderDetail.getQuantity());
                        userFoodItemOutDTO.setPrice(foodItem.getPrice() * orderDetail.getQuantity());
                        foodItemOutDTOS.add(userFoodItemOutDTO);
                    }
                    userOrderDetailsOutDTO.setFoodItemOutDTOS(foodItemOutDTOS);
                    userOrderDetailsOutDTOS.add(userOrderDetailsOutDTO);
                } catch (Exception e) {
                    throw new RuntimeException("An unexpected error occurred. Try Again");
                }
            }
            return userOrderDetailsOutDTOS;
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }
}
