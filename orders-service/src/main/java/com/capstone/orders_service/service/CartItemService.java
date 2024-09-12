package com.capstone.orders_service.service;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;

import java.util.List;

public interface CartItemService {
    String addToCart(AddToCartInDTO addToCartInDTO);
    String deleteCartItem(long cartItemId);
    List<CartItemOutDTO> getCartItems(long userId);
    String updateCartItem(long cartItemId, int index);
}
