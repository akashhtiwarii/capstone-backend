package com.capstone.orders_service.dtoTest;

import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.CartItemOutDTO;
import org.junit.jupiter.api.Test;

public class CartItemOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();

        assertEquals(0, cartItemOutDTO.getCartItemId());
        long cartItemId = 1L;
        cartItemOutDTO.setCartItemId(cartItemId);
        assertEquals(cartItemId, cartItemOutDTO.getCartItemId());

        assertEquals(0, cartItemOutDTO.getUserId());
        long userId = 2L;
        cartItemOutDTO.setUserId(userId);
        assertEquals(userId, cartItemOutDTO.getUserId());

        assertNull(cartItemOutDTO.getRestaurantName());
        String restaurantName = "Restaurant A";
        cartItemOutDTO.setRestaurantName(restaurantName);
        assertEquals(restaurantName, cartItemOutDTO.getRestaurantName());

        assertNull(cartItemOutDTO.getFoodName());
        String foodName = "Food Item";
        cartItemOutDTO.setFoodName(foodName);
        assertEquals(foodName, cartItemOutDTO.getFoodName());

        assertEquals(0, cartItemOutDTO.getQuantity());
        int quantity = 3;
        cartItemOutDTO.setQuantity(quantity);
        assertEquals(quantity, cartItemOutDTO.getQuantity());

        assertEquals(0.0, cartItemOutDTO.getPrice());
        double price = 10.5;
        cartItemOutDTO.setPrice(price);
        assertEquals(price, cartItemOutDTO.getPrice(), 0.001);
    }

    @Test
    public void testEqualsAndHashcode() {
        long cartItemId = 1L;
        long userId = 2L;
        String restaurantName = "Restaurant A";
        String foodName = "Food Item";
        int quantity = 3;
        double price = 10.5;

        CartItemOutDTO cartItemOutDTO1 = buildCartItemOutDTO(cartItemId, userId, restaurantName, foodName, quantity, price);

        assertEquals(cartItemOutDTO1, cartItemOutDTO1);
        assertEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO1.hashCode());

        assertNotEquals(cartItemOutDTO1, new Object());

        CartItemOutDTO cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId, restaurantName, foodName, quantity, price);
        assertEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId + 1, userId, restaurantName, foodName, quantity, price);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId + 1, restaurantName, foodName, quantity, price);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId, "Restaurant B", foodName, quantity, price);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId, restaurantName, "Food Item B", quantity, price);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId, restaurantName, foodName, quantity + 1, price);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO2 = buildCartItemOutDTO(cartItemId, userId, restaurantName, foodName, quantity, price + 1.0);
        assertNotEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertNotEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());

        cartItemOutDTO1 = new CartItemOutDTO();
        cartItemOutDTO2 = new CartItemOutDTO();
        assertEquals(cartItemOutDTO1, cartItemOutDTO2);
        assertEquals(cartItemOutDTO1.hashCode(), cartItemOutDTO2.hashCode());
    }

    private CartItemOutDTO buildCartItemOutDTO(long cartItemId, long userId, String restaurantName, String foodName, int quantity, double price) {
        CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();

        cartItemOutDTO.setCartItemId(cartItemId);
        cartItemOutDTO.setUserId(userId);
        cartItemOutDTO.setRestaurantName(restaurantName);
        cartItemOutDTO.setFoodName(foodName);
        cartItemOutDTO.setQuantity(quantity);
        cartItemOutDTO.setPrice(price);

        return cartItemOutDTO;
    }
}
