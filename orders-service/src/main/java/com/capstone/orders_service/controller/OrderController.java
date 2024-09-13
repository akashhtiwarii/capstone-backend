package com.capstone.orders_service.controller;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.service.CartItemService;
import com.capstone.orders_service.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    private final Logger logger = LogManager.getLogger(OrderController.class);
    
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemService cartItemService;

    /**
     * Retrieves orders for a specific restaurant.
     *
     * @param loggedInUserId The ID of the logged-in user.
     * @param restaurantId The ID of the restaurant.
     * @return A list of orders.
     */
    @GetMapping("/myorders")
    public ResponseEntity<List<OrderOutDTO>> getOrders(
            @RequestParam @Min(value = 1, message = "UserId not Valid") long loggedInUserId,
            @RequestParam @Min(value = 1, message = "RestaurantId not Valid") long restaurantId) {
        logger.info("Fetching orders for restaurant : {}", restaurantId);
        List<OrderOutDTO> orders = orderService.getOrders(loggedInUserId, restaurantId);
        logger.info("Fetched orders for restaurant : {}", restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    /**
     * Adds a new order for a user.
     *
     * @param userId The ID of the user placing the order.
     * @param addressId
     * @return A response message indicating the result of the operation.
     */
    @PostMapping("/add")
    public ResponseEntity<RequestSuccessOutDTO> addOrder(@RequestParam @Min(value = 1, message = "UserId not Valid") long userId,
                                                         @RequestParam @Min(value = 1, message = "AddressId not Valid") long addressId) {
        logger.info("Adding orders for user : {}", userId);
        String response = orderService.addOrder(userId, addressId);
        logger.info("Added orders for user : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves items currently in the user's cart.
     *
     * @param userId The ID of the user whose cart items are to be retrieved.
     * @return A list of cart items.
     */
    @GetMapping("/mycart")
    public ResponseEntity<List<CartItemOutDTO>> getMyCart(@RequestParam @Min(value = 1, message = "UserId not Valid") long userId) {
        logger.info("Fetching Cart for user : {}", userId);
        List<CartItemOutDTO> cartItemOutDTOS = cartItemService.getCartItems(userId);
        logger.info("Fetched Cart for user : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemOutDTOS);
    }

    /**
     * Adds an item to the cart.
     *
     * @param addToCartInDTO The details of the item to be added to the cart.
     * @return A response message indicating the result of the operation.
     */
    @PostMapping("/cart/add")
    public ResponseEntity<RequestSuccessOutDTO> addToCart(@Valid @RequestBody AddToCartInDTO addToCartInDTO) {
        logger.info("Adding to Cart for user : {}", addToCartInDTO.getUserId());
        String response = cartItemService.addToCart(addToCartInDTO);
        logger.info("Added to Cart for user : {}", addToCartInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Updates the status of an order.
     *
     * @param ownerId The ID of the owner (user).
     * @param orderId The ID of the order to be updated.
     * @param status The new status for the order.
     * @return A response message indicating the result of the operation.
     */
    @PutMapping("/update")
    public ResponseEntity<RequestSuccessOutDTO> updateOrder(
            @RequestParam @Min(value = 1, message = "OwnerId not Valid") long ownerId,
            @RequestParam @Min(value = 1, message = "OrderId not Valid") long orderId,
            @RequestParam Status status) {
        String response = orderService.updateOrder(ownerId, orderId, status);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves order details for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A list of order details for the restaurant.
     */
    @GetMapping("/restaurantId")
    public ResponseEntity<List<RestaurantOrderDetailsOutDTO>> getRestaurantOrderDetails(
            @RequestParam @Min(value = 1, message = "Valid Restaurant ID required") long restaurantId) {
        List<RestaurantOrderDetailsOutDTO> restaurantOrderDetailsOutDTOS = orderService.getOrderDetails(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOrderDetailsOutDTOS);
    }

    /**
     * Retrieves orders for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of orders for the user.
     */
    @GetMapping("/user/orders")
    public ResponseEntity<List<UserOrderDetailsOutDTO>> getUserOrders(
            @RequestParam @Min(value = 1, message = "Valid UserID required") long userId
    ) {
        List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = orderService.getUserOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userOrderDetailsOutDTOS);
    }

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartItemId The ID of the cart item to be updated.
     * @param delta The change in quantity (can be positive or negative).
     * @return A response message indicating the result of the operation.
     */
    @PutMapping("/cart/update")
    public ResponseEntity<RequestSuccessOutDTO> updateCartItem(
            @RequestParam @Min(value = 1, message = "Valid Cart Item ID is required") long cartItemId,
            @RequestParam int delta
    ) {
        String message = cartItemService.updateCartItem(cartItemId, delta);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Deletes an item from the cart.
     *
     * @param cartItemId The ID of the cart item to be deleted.
     * @return A response message indicating the result of the operation.
     */
    @DeleteMapping("/cart/delete")
    public ResponseEntity<RequestSuccessOutDTO> deleteCartItem(
            @RequestParam @Min(value = 1, message = "Valid Cart Item ID is required") long cartItemId
    ) {
        String message = cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Cancels an order.
     *
     * @param orderId The ID of the order to be canceled.
     * @return A response message indicating the result of the operation.
     */
    @PutMapping("/cancel")
    public ResponseEntity<RequestSuccessOutDTO> cancelOrder(
            @RequestParam @Min(value = 1, message = "Valid Order ID is required") long orderId
    ) {
        String response = orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }
}
