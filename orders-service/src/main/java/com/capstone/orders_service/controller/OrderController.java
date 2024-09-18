package com.capstone.orders_service.controller;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemsListOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.RequestSuccessOutDTO;
import com.capstone.orders_service.dto.RestaurantOrderDetailsOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import com.capstone.orders_service.service.CartItemService;
import com.capstone.orders_service.service.OrderService;
import com.capstone.orders_service.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Controller for managing orders and cart items in the ordering service.
 * Provides endpoints for retrieving, adding, updating, and deleting orders and cart items.
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    /**
     * Logger for logging operations in the OrderController.
     */
    private final Logger logger = LogManager.getLogger(OrderController.class);

    /**
     * Service for handling operations related to orders.
     */
    @Autowired
    private OrderService orderService;

    /**
     * Service for handling operations related to cart items.
     */
    @Autowired
    private CartItemService cartItemService;

    /**
     * Retrieves orders for a specific restaurant.
     *
     * @param loggedInUserId The ID of the logged-in user.
     * @param restaurantId The ID of the restaurant.
     * @return A ResponseEntity containing a list of orders for the specified restaurant.
     */
    @GetMapping(Constants.GET_RESTAURANT_ORDERS)
    public ResponseEntity<List<OrderOutDTO>> getOrders(
            @RequestParam @Min(value = 1, message = Constants.USER_ID_NOT_VALID) final long loggedInUserId,
            @RequestParam @Min(value = 1, message = Constants.RESTAURANT_ID_NOT_VALID) final long restaurantId) {
        logger.info("Fetching orders for restaurant : {}", restaurantId);
        List<OrderOutDTO> orders = orderService.getOrders(loggedInUserId, restaurantId);
        logger.info("Fetched orders for restaurant : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    /**
     * Adds a new order for a user.
     *
     * @param userId The ID of the user placing the order.
     * @param addressId The ID of the address for the order.
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @PostMapping(Constants.ADD_ORDER)
    public ResponseEntity<RequestSuccessOutDTO> addOrder(
            @RequestParam @Min(value = 1, message = Constants.USER_ID_NOT_VALID) final long userId,
            @RequestParam @Min(value = 1, message = Constants.ADDRESS_ID_NOT_VALID) final long addressId) {
        logger.info("Adding orders for user : {}", userId);
        String response = orderService.addOrder(userId, addressId);
        logger.info("Added orders for user : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves items currently in the user's cart.
     *
     * @param userId The ID of the user whose cart items are to be retrieved.
     * @return A ResponseEntity containing a list of cart items for the specified user.
     */
    @GetMapping(Constants.MY_CART)
    public ResponseEntity<CartItemsListOutDTO> getMyCart(
            @RequestParam @Min(value = 1, message = Constants.USER_ID_NOT_VALID) final long userId
    ) {
        logger.info("Fetching Cart for user : {}", userId);
        CartItemsListOutDTO cartItemsListOutDTO = cartItemService.getCartItems(userId);
        logger.info("Fetched Cart for user : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemsListOutDTO);
    }

    /**
     * Adds an item to the cart.
     *
     * @param addToCartInDTO The details of the item to be added to the cart.
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @PostMapping(Constants.ADD_TO_CART)
    public ResponseEntity<RequestSuccessOutDTO> addToCart(@Valid @RequestBody final AddToCartInDTO addToCartInDTO) {
        logger.info("Adding to Cart for user : {}", addToCartInDTO.getUserId());
        String response = cartItemService.addToCart(addToCartInDTO);
        logger.info("Added to Cart for user : {}", addToCartInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Updates the status of an order.
     *
     * @param ownerId The ID of the owner (user) performing the update.
     * @param orderId The ID of the order to be updated.
     * @param status The new status for the order.
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @PutMapping(Constants.UPDATE_ORDER)
    public ResponseEntity<RequestSuccessOutDTO> updateOrder(
            @RequestParam @Min(value = 1, message = Constants.OWNER_ID_NOT_VALID) final long ownerId,
            @RequestParam @Min(value = 1, message = Constants.ORDER_ID_NOT_VALID) final long orderId,
            @RequestParam final Status status) {
        logger.info("Updating order for orderID : {}", orderId);
        String response = orderService.updateOrder(ownerId, orderId, status);
        logger.info("Updated order for orderID : {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves order details for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A ResponseEntity containing a list of order details for the specified restaurant.
     */
    @GetMapping(Constants.GET_RESTAURANT_ORDER_DETAILS)
    public ResponseEntity<List<RestaurantOrderDetailsOutDTO>> getRestaurantOrderDetails(
            @RequestParam @Min(value = 1, message = Constants.RESTAURANT_ID_NOT_VALID) final long restaurantId) {
        logger.info("Fetching order details for restaurantId : {}", restaurantId);
        List<RestaurantOrderDetailsOutDTO> restaurantOrderDetailsOutDTOS = orderService.getOrderDetails(restaurantId);
        logger.info("Fetched order details for restaurantId : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOrderDetailsOutDTOS);
    }

    /**
     * Retrieves orders for a specific user.
     *
     * @param userId The ID of the user.
     * @return A ResponseEntity containing a list of orders for the specified user.
     */
    @GetMapping(Constants.GET_USER_ORDERS)
    public ResponseEntity<List<UserOrderDetailsOutDTO>> getUserOrders(
            @RequestParam @Min(value = 1, message = Constants.USER_ID_NOT_VALID) final long userId
    ) {
        logger.info("Fetching order details for UserID : {}", userId);
        List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = orderService.getUserOrders(userId);
        logger.info("Fetched order details for UserID : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(userOrderDetailsOutDTOS);
    }

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartItemId The ID of the cart item to be updated.
     * @param delta The change in quantity (can be positive or negative).
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @PutMapping(Constants.UPDATE_CART)
    public ResponseEntity<RequestSuccessOutDTO> updateCartItem(
            @RequestParam @Min(value = 1, message = Constants.CART_ID_NOT_VALID) final long cartItemId,
            @RequestParam final int delta
    ) {
        logger.info("Updating cart item for ID : {}", cartItemId);
        String message = cartItemService.updateCartItem(cartItemId, delta);
        logger.info("Updating cart item for ID : {}", cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Deletes an item from the cart.
     *
     * @param cartItemId The ID of the cart item to be deleted.
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @DeleteMapping(Constants.DELETE_CART)
    public ResponseEntity<RequestSuccessOutDTO> deleteCartItem(
            @RequestParam @Min(value = 1, message = Constants.CART_ID_NOT_VALID) final long cartItemId
    ) {
        logger.info("Deleting cart item with ID : {}", cartItemId);
        String message = cartItemService.deleteCartItem(cartItemId);
        logger.info("Deleted cart item with ID : {}", cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Cancels an order.
     *
     * @param orderId The ID of the order to be canceled.
     * @return A ResponseEntity containing a message indicating the result of the operation.
     */
    @PutMapping(Constants.CANCEL_ORDER)
    public ResponseEntity<RequestSuccessOutDTO> cancelOrder(
            @RequestParam @Min(value = 1, message = Constants.ORDER_ID_NOT_VALID) final long orderId
    ) {
        logger.info("Cancelling order with ID : {}", orderId);
        String response = orderService.cancelOrder(orderId);
        logger.info("Cancelling order with ID : {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }
}
