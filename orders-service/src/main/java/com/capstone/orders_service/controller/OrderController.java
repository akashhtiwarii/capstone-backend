package com.capstone.orders_service.controller;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;
import com.capstone.orders_service.dto.OrderDetailsOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import com.capstone.orders_service.service.CartItemService;
import com.capstone.orders_service.service.OrderService;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/myorders")
    public ResponseEntity<List<OrderOutDTO>> getOrders(
            @RequestParam @Min(value = 1, message = "UserId not Valid") long loggedInUserId,
            @RequestParam @Min(value = 1, message = "RestaurantId not Valid") long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders(loggedInUserId, restaurantId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestParam @Min(value = 1, message = "UserId not Valid") long userId) {
        String response = orderService.addOrder(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/mycart")
    public ResponseEntity<List<CartItemOutDTO>> getMyCart(@RequestParam @Min(value = 1, message = "UserId not Valid") long userId) {
        List<CartItemOutDTO> cartItemOutDTOS = cartItemService.getCartItems(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartItemOutDTOS);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartInDTO addToCartInDTO) {
        String response = cartItemService.addToCart(addToCartInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(
            @RequestParam @Min(value = 1, message = "OwnerId not Valid") long ownerId,
            @RequestParam @Min(value = 1, message = "OrderId not Valid") long orderId,
            @RequestParam Status status) {
        String response = orderService.updateOrder(ownerId, orderId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/restaurantId")
    public ResponseEntity<List<OrderDetailsOutDTO>> getRestaurantOrderDetails(
            @RequestParam @Min(value = 1, message = "Valid Restaurant ID required") long restaurantId) {
        List<OrderDetailsOutDTO> orderDetailsOutDTOS = orderService.getOrderDetails(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDetailsOutDTOS);
    }

    @GetMapping("/user/orders")
    public ResponseEntity<List<UserOrderDetailsOutDTO>> getUserOrders(
            @RequestParam @Min(value = 1, message = "Valid UserID required") long userId
    ) {
        List<UserOrderDetailsOutDTO> userOrderDetailsOutDTOS = orderService.getUserOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userOrderDetailsOutDTOS);
    }

    @PutMapping("/cart/update")
    public ResponseEntity<String> updateCartItem(
            @RequestParam @Min(value = 1, message = "Valid Cart Item ID is required") long cartItemId,
            @RequestParam int delta
    ) {
        String message = cartItemService.updateCartItem(cartItemId, delta);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/cart/delete")
    public ResponseEntity<String> deleteCartItem(
            @RequestParam @Min(value = 1, message = "Valid Cart Item ID is required") long cartItemId
    ) {
        String message = cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/order/cancel")
    public ResponseEntity<String> cancelOrder(
            @RequestParam @Min(value = 1, message = "Valid Order ID is required") long orderId
    ) {
        String message =
    }
}
