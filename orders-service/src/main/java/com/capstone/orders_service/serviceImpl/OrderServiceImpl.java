package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.converters.OrderConverter;
import com.capstone.orders_service.dto.AddressInDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.entity.Order;
import com.capstone.orders_service.entity.OrderDetail;
import com.capstone.orders_service.exceptions.AddressNotFoundException;
import com.capstone.orders_service.exceptions.CartItemDoesNotExistsException;
import com.capstone.orders_service.exceptions.OrderNotFoundException;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.exceptions.ResourceNotValidException;
import com.capstone.orders_service.exceptions.UserNotFoundException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.repository.OrderDetailRepository;
import com.capstone.orders_service.repository.OrderRepository;
import com.capstone.orders_service.service.OrderService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        AddressInDTO addressInDTO;
        try {
            user = usersFeignClient.getUserById(userId).getBody();
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User Not Found");
        }
        try {
            addressInDTO = usersFeignClient.getAddressById(userId).getBody();
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
        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(cartItems.get(0).getRestaurantId());
        order.setAddressId(addressInDTO.getAddressId());
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
}
