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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public List<OrderOutDTO> getOrders(final long loggedInUserId, final long restaurantId) {
        try {
            UserOutDTO user = usersFeignClient.getUserById(loggedInUserId).getBody();
            RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(restaurantId).getBody();
            if (user == null) {
                log.error("User Not Found : {}", loggedInUserId);
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (restaurant == null) {
                log.error("Restaurant Not Found : {}", restaurantId);
                throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
            }
            if (restaurant.getOwnerId() != user.getUserId()) {
                log.error("User Not Valid : {}", user.getUserId());
                throw new ResourceNotValidException(Constants.USER_NOT_VALID);
            }
            List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
            if (orders.isEmpty()) {
                log.error("Orders Not Found");
                throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
            }
            List<OrderOutDTO> orderOutDTOS = new ArrayList<>();
            for (Order order : orders) {
                orderOutDTOS.add(OrderConverter.orderToOrderOutDTO(order));
            }
            return orderOutDTOS;
        } catch (FeignException.NotFound e) {
            log.error("Data Not Found");
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        } catch (FeignException e) {
            log.error("Service Down");
            throw new RuntimeException(Constants.SERVICE_DOWN);
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
    public String addOrder(final long userId, final long addressId) {
        UserOutDTO user;
        AddressOutDTO addressOutDTO;
        WalletOutDTO wallet;
        try {
            user = usersFeignClient.getUserById(userId).getBody();
            if (user == null) {
                log.error("User Not Found : {}", userId);
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            log.error("User Not Found");
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
        try {
            addressOutDTO = usersFeignClient.getAddressById(addressId).getBody();
            if (addressOutDTO == null) {
                log.error("Address Not Found");
                throw new ResourceNotFoundException(Constants.ADDRESS_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            log.error("Address Not Found");
            throw new ResourceNotFoundException(Constants.ADDRESS_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            log.error("Cart Item Not Found");
            throw new ResourceNotFoundException(Constants.CART_ITEM_NOT_FOUND);
        }
        double price = 0.0;
        for (CartItem cartItem : cartItems) {
            price += cartItem.getPrice() * cartItem.getQuantity();
        }
        try {
            wallet = usersFeignClient.getUserWallet(userId).getBody();
            if (wallet == null) {
                log.error("Wallet Not Found");
                throw new ResourceNotFoundException(Constants.WALLET_NOT_FOUND);
            }
            if (wallet.getAmount() < price) {
                log.error("Insufficient Amount in Wallet");
                throw new InsufficientAmountException(Constants.INSUFFICIENT_WALLET_AMOUNT);
            }
            double updatedAmount = wallet.getAmount() - price;
            usersFeignClient.updateUserWallet(user.getUserId(), updatedAmount).getBody();
        } catch (FeignException.NotFound e) {
            log.error("User Not Found");
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
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
            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(savedOrder.getOrderId());
                orderDetail.setPrice(cartItem.getPrice());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setFoodId(cartItem.getFoodId());
                orderDetailRepository.save(orderDetail);
                cartItemRepository.delete(cartItem);
            }
            return Constants.ORDER_ADDED_SUCCESSFULLY;
        } catch (Exception e) {
            log.error("Unexpected Error Occurred");
            throw new RuntimeException(Constants.UNEXPECTED_ERROR + e.getMessage());
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
    public String deleteOrder(final long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            log.error("Order Not Found");
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailRepository.delete(orderDetail);
        }
        orderRepository.delete(order);
        return Constants.ORDER_DELETED_SUCCESSFULLY;
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
    public String updateOrder(final long userId, final long orderId, final Status status) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            log.error("Order Not Found");
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        try {
            RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(order.getRestaurantId()).getBody();
            if (restaurant == null) {
                log.error("Restaurant Not Found");
                throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            log.error("Restaurant Not Found");
            throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
        } catch (FeignException e) {
            log.error("Restaurant Service Down");
            throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
        }
        order.setStatus(status);
        orderRepository.save(order);
        return Constants.ORDER_UPDATED_SUCCESSFULLY;
    }

    /**
     * Retrieves the details of all orders for a specific restaurant.
     *
     * @param restaurantId the ID of the restaurant whose orders are being retrieved
     * @return a list of {@link RestaurantOrderDetailsOutDTO} representing the order details for the restaurant
     * @throws ResourceNotFoundException if no orders are found
     */
    @Override
    public List<RestaurantOrderDetailsOutDTO> getOrderDetails(final long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        String address = "";
        if (orders.isEmpty()) {
            log.error("Order Not Found");
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        List<RestaurantOrderDetailsOutDTO> restaurantOrderDetailsOutDTOS = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() != Status.CANCELLED) {
                RestaurantOrderDetailsOutDTO restaurantOrderDetailsOutDTO = new RestaurantOrderDetailsOutDTO();
                restaurantOrderDetailsOutDTO.setOrderId(order.getOrderId());
                try {
                    UserOutDTO user = usersFeignClient.getUserById(order.getUserId()).getBody();
                    if (user == null) {
                        log.error("User Not Found");
                        throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
                    }
                    restaurantOrderDetailsOutDTO.setUserName(user.getName());
                } catch (FeignException.NotFound e) {
                    log.error("User Not Found");
                    throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
                } catch (FeignException e) {
                    log.error("User Service Down");
                    throw new RuntimeException(Constants.USER_SERVICE_DOWN);
                }
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());
                List<OrderDetailOutDTO> orderDetailOutDTOS = new ArrayList<>();
                for (OrderDetail orderDetail : orderDetails) {
                    OrderDetailOutDTO orderDetailOutDTO = new OrderDetailOutDTO();
                    try {
                        FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(
                                orderDetail.getFoodId()
                        ).getBody();
                        if (foodItemOutDTO == null) {
                            log.error("Food Item Not Found");
                            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
                        }
                        orderDetailOutDTO.setFoodName(foodItemOutDTO.getName());
                        orderDetailOutDTO.setQuantity(orderDetail.getQuantity());
                        orderDetailOutDTO.setPrice(orderDetail.getPrice() * orderDetail.getQuantity());
                    } catch (FeignException.NotFound e) {
                        log.error("Food Item Not Found");
                        throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
                    } catch (FeignException e) {
                        log.error("Restaurant Service Down");
                        throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
                    }
                    orderDetailOutDTOS.add(orderDetailOutDTO);
                }
                try {
                    AddressOutDTO userAddress = usersFeignClient.getAddressById(order.getAddressId()).getBody();
                    if (userAddress == null) {
                        log.error("Address Not Found");
                        throw new ResourceNotFoundException(Constants.ADDRESS_NOT_FOUND);
                    }
                    address = userAddress.toString();
                } catch (FeignException.NotFound e) {
                    log.error("Address Not Found");
                    throw new ResourceNotFoundException(Constants.ADDRESS_NOT_FOUND);
                } catch (FeignException e) {
                    log.error("User Service Down");
                    throw new RuntimeException(Constants.USER_SERVICE_DOWN);
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
    public List<UserOrderDetailsOutDTO> getUserOrders(final long userId) {
        try {
            UserOutDTO user = usersFeignClient.getUserById(userId).getBody();
            if (user == null) {
                log.error("User Not Found");
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            List<Order> orders = orderRepository.findByUserId(user.getUserId());
            if (orders.isEmpty()) {
                log.error("Order Not Found");
                throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
            }
            List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = new ArrayList<>();
            for (Order order : orders) {
                try {
                    RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(
                            order.getRestaurantId()
                    ).getBody();
                    if (restaurant == null) {
                        log.error("Restaurant Not Found");
                        throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
                    }
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
                        if (foodItem == null) {
                            log.error("Food Item Not Found");
                            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
                        }
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
                    log.error("Unexpected Error Occurred");
                    throw new RuntimeException(Constants.UNEXPECTED_ERROR);
                }
            }
            return userOrderDetailsOutDTOS;
        } catch (FeignException.NotFound e) {
            log.error("Data Not Found");
            throw new ResourceNotFoundException(Constants.DATA_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
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
    public String cancelOrder(final long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            log.error("Order Not Found");
            throw new ResourceNotFoundException(Constants.ORDER_NOT_FOUND);
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeDifference = Duration.between(order.getOrderTime(), currentTime);
        if (timeDifference.getSeconds() > Constants.TIME_TO_CANCEL_ORDER) {
            log.error("Cannot Cancel Order Now");
            throw new ResourceNotValidException(Constants.CANNOT_CANCEL_ORDER + timeDifference.getSeconds());
        }
        try {
            WalletOutDTO wallet = usersFeignClient.getUserWallet(order.getUserId()).getBody();
            if (wallet == null) {
                log.error("Wallet Not Found");
                throw new ResourceNotFoundException(Constants.WALLET_NOT_FOUND);
            }
            double updatedAmount = wallet.getAmount() + order.getPrice();
            try {
                usersFeignClient.updateUserWallet(order.getUserId(), updatedAmount);
            } catch (FeignException.NotFound e) {
                log.error("Unexpected Error Occurred");
                throw new RuntimeException(Constants.UNEXPECTED_ERROR);
            } catch (FeignException e) {
                log.error("User Service Down");
                throw new RuntimeException(Constants.USER_SERVICE_DOWN);
            }
        } catch (FeignException.NotFound e) {
            log.error("Wallet Not Found");
            throw new ResourceNotFoundException(Constants.WALLET_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
        return Constants.ORDER_CANCELLED_SUCCESSFULLY;
    }
}
