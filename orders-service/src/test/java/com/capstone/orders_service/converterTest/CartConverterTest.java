package com.capstone.orders_service.converterTest;


import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.entity.CartItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartConverterTest {

    @Test
    void testAddToCartInDTOToCartEntity() {
        AddToCartInDTO addToCartInDTO = new AddToCartInDTO();
        addToCartInDTO.setUserId(1L);
        addToCartInDTO.setRestaurantId(2L);
        addToCartInDTO.setFoodId(3L);
        addToCartInDTO.setQuantity(5);

        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);

        assertEquals(1L, cartItem.getUserId());
        assertEquals(2L, cartItem.getRestaurantId());
        assertEquals(3L, cartItem.getFoodId());
        assertEquals(5, cartItem.getQuantity());
    }
}

