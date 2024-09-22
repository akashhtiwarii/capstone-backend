package com.capstone.orders_service.entityTest;

import com.capstone.orders_service.entity.CartItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CartItemTest {

    @Test
    public void testGetterAndSetter() {
        CartItem cartItem = new CartItem();

        assertEquals(0, cartItem.getCartItemId());
        long cartItemId = 1L;
        cartItem.setCartItemId(cartItemId);
        assertEquals(cartItemId, cartItem.getCartItemId());

        assertEquals(0, cartItem.getUserId());
        long userId = 2L;
        cartItem.setUserId(userId);
        assertEquals(userId, cartItem.getUserId());

        assertEquals(0, cartItem.getRestaurantId());
        long restaurantId = 3L;
        cartItem.setRestaurantId(restaurantId);
        assertEquals(restaurantId, cartItem.getRestaurantId());

        assertEquals(0, cartItem.getFoodId());
        long foodId = 4L;
        cartItem.setFoodId(foodId);
        assertEquals(foodId, cartItem.getFoodId());

        assertEquals(0, cartItem.getQuantity());
        int quantity = 5;
        cartItem.setQuantity(quantity);
        assertEquals(quantity, cartItem.getQuantity());

        assertEquals(0.0, cartItem.getPrice(), 0.0);
        double price = 20.50;
        cartItem.setPrice(price);
        assertEquals(price, cartItem.getPrice(), 0.0);
    }

    @Test
    public void testToString() {
        CartItem cartItem = new CartItem();

        long cartItemId = 1L;
        cartItem.setCartItemId(cartItemId);

        long userId = 2L;
        cartItem.setUserId(userId);

        long restaurantId = 3L;
        cartItem.setRestaurantId(restaurantId);

        long foodId = 4L;
        cartItem.setFoodId(foodId);

        int quantity = 5;
        cartItem.setQuantity(quantity);

        double price = 20.50;
        cartItem.setPrice(price);

        assertEquals("CartItem(cartItemId=1, userId=2, restaurantId=3, foodId=4, quantity=5, price=20.5)", cartItem.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        long cartItemId = 1L;
        long userId = 2L;
        long restaurantId = 3L;
        long foodId = 4L;
        int quantity = 5;
        double price = 20.50;

        CartItem cartItem1 = buildCartItem(cartItemId, userId, restaurantId, foodId, quantity, price);

        assertEquals(cartItem1, cartItem1);
        assertEquals(cartItem1.hashCode(), cartItem1.hashCode());

        assertNotEquals(cartItem1, new Object());

        CartItem cartItem2 = buildCartItem(cartItemId, userId, restaurantId, foodId, quantity, price);
        assertEquals(cartItem1, cartItem2);
        assertEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId + 1, userId, restaurantId, foodId, quantity, price);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId, userId + 1, restaurantId, foodId, quantity, price);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId, userId, restaurantId + 1, foodId, quantity, price);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId, userId, restaurantId, foodId + 1, quantity, price);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId, userId, restaurantId, foodId, quantity + 1, price);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem2 = buildCartItem(cartItemId, userId, restaurantId, foodId, quantity, price + 10);
        assertNotEquals(cartItem1, cartItem2);
        assertNotEquals(cartItem1.hashCode(), cartItem2.hashCode());

        cartItem1 = new CartItem();
        cartItem2 = new CartItem();
        assertEquals(cartItem1, cartItem2);
        assertEquals(cartItem1.hashCode(), cartItem2.hashCode());
    }

    private CartItem buildCartItem(long cartItemId, long userId, long restaurantId, long foodId, int quantity, double price) {
        return new CartItem(cartItemId, userId, restaurantId, foodId, quantity, price);
    }
}