package com.capstone.orders_service.controller;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
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
    public ResponseEntity<List<OrderOutDTO>> getOrders(@RequestParam long loggedInUserId, @RequestParam long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders(loggedInUserId, restaurantId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestParam @Min(value = 1, message = "UserId not Valid") long userId) {
        String response = orderService.addOrder(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartInDTO addToCartInDTO) {
        String response = cartItemService.addToCart(addToCartInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
