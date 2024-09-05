package com.capstone.orders_service.service;

import com.capstone.orders_service.dto.AddToCartInDTO;

public interface CartService {
    String addToCart(AddToCartInDTO addToCartInDTO);
    String deleteCartItem(long cartItemId);
}
