package com.capstone.orders_service.converters;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;
import com.capstone.orders_service.entity.CartItem;

public class CartConverter {
    public static CartItem addToCartInDTOToCartEntity(AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(addToCartInDTO.getUserId());
        cartItem.setRestaurantId(addToCartInDTO.getRestaurantId());
        cartItem.setFoodId(addToCartInDTO.getFoodId());
        cartItem.setQuantity(addToCartInDTO.getQuantity());
        cartItem.setPrice(addToCartInDTO.getPrice());
        return cartItem;
    }

    public static CartItemOutDTO cartEntityToCartItemOutDTO(CartItem cartItem) {
        CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();
        cartItemOutDTO.setUserId(cartItem.getUserId());
        cartItemOutDTO.setRestaurantId(cartItem.getRestaurantId());
        cartItemOutDTO.setFoodId(cartItem.getFoodId());
        cartItemOutDTO.setQuantity(cartItem.getQuantity());
        cartItemOutDTO.setPrice(cartItem.getPrice());
        return cartItemOutDTO;
    }
}
