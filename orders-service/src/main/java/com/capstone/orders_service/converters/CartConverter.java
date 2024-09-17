package com.capstone.orders_service.converters;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.entity.CartItem;

/**
 * This class provides methods to convert data transfer objects (DTOs) to entity objects for cart operations.
 */
public final class CartConverter {

    /**
     * Private constructor for CategoryConverters.
     */
    private CartConverter() {
        throw new UnsupportedOperationException("Utility Class Cannot be instantiated");
    }

    /**
     * Converts an {@link AddToCartInDTO} object to a {@link CartItem} entity.
     *
     * @param addToCartInDTO the DTO containing cart item details to be converted.
     * @return a {@link CartItem} entity populated with the values from the provided DTO.
     */
    public static CartItem addToCartInDTOToCartEntity(final AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(addToCartInDTO.getUserId());
        cartItem.setRestaurantId(addToCartInDTO.getRestaurantId());
        cartItem.setFoodId(addToCartInDTO.getFoodId());
        cartItem.setQuantity(addToCartInDTO.getQuantity());
        return cartItem;
    }
}
