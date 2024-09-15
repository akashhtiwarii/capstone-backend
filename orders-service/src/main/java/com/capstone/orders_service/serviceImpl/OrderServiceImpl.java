package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.converters.OrderConverter;
import com.capstone.orders_service.dto.AddressOutDTO;
import com.capstone.orders_service.dto.FoodItemOutDTO;
import com.capstone.orders_service.dto.OrderDetailOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.RestaurantOrderDetailsOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import com.capstone.orders_service.dto.UserFoodItemOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.dto.WalletOutDTO;
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
import com.capstone.orders_service.service.OrderService;
import com.capstone.orders_service.utils.Constants;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link OrderService} interface. This service handles operations related to
 * orders including creating, updating, retrieving, and deleting orders. It communicates with external services
 * like the user and restaurant services using Feign clients.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Repository for managing {@link Order} entities in the database.
     */
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Repository for managing {@link OrderDetail} entities in the database.
     */
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * Repository for managing {@link CartItem} entities in the database.
     */
    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Feign client for communicating with the restaurant microservice.
     */
    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    /**
     * Feign client for communicating with the user microservice.
     */
    @Autowired
    private UsersFeignClient usersFeignClient;

    /**
     * Retrieves all orders for a specific restaurant, provided that the user making the request is the owner of
     * the restaurant.
     *
     * @param loggedInUserId the ID of the user making the request
     * @param restaurantId   the ID of the restaurant whose orders are being retrieved
     * @return a list of {@link OrderOutDTO} representing the orders of the restaurant
     * @throws ResourceNotFoundException   if no orders are found or if the restaurant or user does not exist
     * @throws ResourceNotValidException   if the user does not own the restaurant
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
     * Adds an order for the given user and address, deducting the price from the user's wallet. If the user has
     * insufficient funds or no items in the cart, appropriate exceptions are thrown.
     *
     * @param userId    the ID of the user making the order
     * @param addressId the ID of the address for delivery
     * @return a success message indicating the order was added successfully
     * @throws ResourceNotFoundException   if the user, address, or wallet is not found
     * @throws InsufficientAmountException if the user's wallet does not have enough funds for the order
     */
    @Override
    public String addOrder(long userId, long addressId) {
        UserOutDTO user;
        AddressOutDTO addressOutDTO;
        WalletOutDTO wallet;
        try {
            user = usersFeignClient.getUserById(userId).getBody();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("User Not Found");
        }
        try {
            addressOutDTO = usersFeignClient.getAddressById(addressId).getBody();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("No Address Added");
        }
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("No Items in your Cart");
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
            throw new ResourceNotFoundException("User not found");
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
     * Deletes an order and its associated details from the database.
     *
     * @param orderId the ID of the order to delete
     * @return a success message indicating the order was deleted successfully
     * @throws ResourceNotFoundException if the order is not found
     */
    @Override
    public String deleteOrder(long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Specified order not present");
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailRepository.delete(orderDetail);
        }
        orderRepository.delete(order);
        return "Order Deleted Successfully";
    }

    /**
     * Updates the status of an order.
     *
     * @param orderId the ID of the order to update
     * @param userId  the ID of the user making the request
     * @param status  the new status to set for the order
     * @return a success message indicating the order was updated successfully
     * @throws ResourceNotFoundException if the order or restaurant is not found
     */
    @Override
    public String updateOrder(long userId, long orderId, Status status) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order Not Found");
        }
        try {
            RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(order.getRestaurantId()).getBody();
            long restaurantId = order.getRestaurantId();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Restaurant Not Found");
        }
        order.setStatus(status);
        orderRepository.save(order);
        return "Order Updated Successfully";
    }

    /**
     * Retrieves the details of all orders for a specific restaurant.
     *
     * @param restaurantId the ID of the restaurant whose orders are being retrieved
     * @return a list of {@link RestaurantOrderDetailsOutDTO} representing the order details for the restaurant
     * @throws ResourceNotFoundException if no orders are found
     */
    @Override
    public List<RestaurantOrderDetailsOutDTO> getOrderDetails(long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        String address = "";
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No Orders Present");
        }
        List<RestaurantOrderDetailsOutDTO> restaurantOrderDetailsOutDTOS = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() != Status.CANCELLED) {
                RestaurantOrderDetailsOutDTO restaurantOrderDetailsOutDTO = new RestaurantOrderDetailsOutDTO();
                restaurantOrderDetailsOutDTO.setOrderId(order.getOrderId());
                try {
                    UserOutDTO user = usersFeignClient.getUserById(order.getUserId()).getBody();
                    restaurantOrderDetailsOutDTO.setUserName(user.getName());
                } catch (FeignException.NotFound e) {
                    throw new ResourceNotFoundException("User Not Found");
                }
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());
                List<OrderDetailOutDTO> orderDetailOutDTOS = new ArrayList<>();
                for (OrderDetail orderDetail : orderDetails) {
                    OrderDetailOutDTO orderDetailOutDTO = new OrderDetailOutDTO();
                    try {
                        FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(
                                orderDetail.getFoodId()
                        ).getBody();
                        orderDetailOutDTO.setFoodName(foodItemOutDTO.getName());
                        orderDetailOutDTO.setQuantity(orderDetail.getQuantity());
                        orderDetailOutDTO.setPrice(orderDetail.getPrice());
                    } catch (FeignException.NotFound e) {
                        throw new ResourceNotFoundException("No Food Item Present");
                    }
                    orderDetailOutDTOS.add(orderDetailOutDTO);
                }
                try {
                    address = usersFeignClient.getAddressByUserId(order.getUserId()).getBody().toString();
                } catch (FeignException.NotFound e) {
                    throw new ResourceNotFoundException("No Address Present for an Order");
                }
                restaurantOrderDetailsOutDTO.setUserId(order.getUserId());
                restaurantOrderDetailsOutDTO.setOrderDetailOutDTOS(orderDetailOutDTOS);
                restaurantOrderDetailsOutDTO.setAddress(address);
                restaurantOrderDetailsOutDTO.setStatus(order.getStatus());
                restaurantOrderDetailsOutDTOS.add(restaurantOrderDetailsOutDTO);
            }
        }
        return restaurantOrderDetailsOutDTOS;
    }

    /**
     * Retrieves all orders placed by a specific user.
     *
     * @param userId the ID of the user whose orders are being retrieved
     * @return a list of {@link UserOrderDetailsOutDTO} representing the user's orders
     * @throws ResourceNotFoundException if no orders are found for the user or if the user does not exist
     */
    @Override
    public List<UserOrderDetailsOutDTO> getUserOrders(long userId) {
        try {
            UserOutDTO user = usersFeignClient.getUserById(userId).getBody();
            List<Order> orders = orderRepository.findByUserId(user.getUserId());
            if (orders.isEmpty()) {
                throw new ResourceNotFoundException("You Do not have any orders");
            }
            List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = new ArrayList<>();
            for (Order order : orders) {
                try {
                    RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(
                            order.getRestaurantId()
                    ).getBody();
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());
                    UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO();
                    userOrderDetailsOutDTO.setOrderId(order.getOrderId());
                    userOrderDetailsOutDTO.setRestaurantName(restaurant.getName());
                    userOrderDetailsOutDTO.setRestaurantEmail(restaurant.getEmail());
                    userOrderDetailsOutDTO.setStatus(order.getStatus());
                    List<UserFoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
                    for (OrderDetail orderDetail : orderDetails) {
                        FoodItemOutDTO foodItem = restaurantFeignClient.getFoodItemById(
                                orderDetail.getFoodId()
                        ).getBody();
                        UserFoodItemOutDTO userFoodItemOutDTO = new UserFoodItemOutDTO();
                        userFoodItemOutDTO.setFoodId(foodItem.getFoodId());
                        userFoodItemOutDTO.setName(foodItem.getName());
                        userFoodItemOutDTO.setQuantity(orderDetail.getQuantity());
                        userFoodItemOutDTO.setPrice(foodItem.getPrice() * orderDetail.getQuantity());
                        foodItemOutDTOS.add(userFoodItemOutDTO);
                    }
                    userOrderDetailsOutDTO.setFoodItemOutDTOS(foodItemOutDTOS);
                    userOrderDetailsOutDTO.setOrderTime(order.getOrderTime());
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

    /**
     * Cancels an order, provided that the cancellation request is made within 30 seconds of placing the order.
     *
     * @param orderId the ID of the order to cancel
     * @return a success message indicating the order was cancelled successfully
     * @throws ResourceNotFoundException   if the order is not found
     * @throws ResourceNotValidException   if the cancellation time exceeds the allowed limit
     */
    @Override
    public String cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order Not Found");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeDifference = Duration.between(order.getOrderTime(), currentTime);
        if (timeDifference.getSeconds() > Constants.TIME_TO_CANCEL_ORDER) {
            throw new ResourceNotValidException("Cannot Cancel the order Now " + timeDifference.getSeconds());
        }
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
        return "Order Cancelled Successfully";
    }
}
