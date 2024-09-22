package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object for representing an item in the cart.
 * This class is used to transfer cart item details from the server to the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemOutDTO {

    /**
     * The unique identifier for the cart item.
     */
    private long cartItemId;

    /**
     * The unique identifier for the user who owns the cart item.
     */
    private long userId;

    /**
     * The name of the restaurant where the food item is available.
     */
    private String restaurantName;

    /**
     * The name of the food item in the cart.
     */
    private String foodName;

    /**
     * The quantity of the food item in the cart.
     */
    private int quantity;

    /**
     * The price of the food item with quantity in the cart.
     */
    private String priceQuantity;

    /**
     * Compares this CartItemOutDTO object to the specified object for equality.
     * Two CartItemOutDTO objects are considered equal
     * if they have the same cartItemId, userId, restaurantName, foodName, quantity, and price.
     * @param o the object to compare this CartItemOutDTO against.
     * @return true if the specified object is equal to this CartItemOutDTO; false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemOutDTO that = (CartItemOutDTO) o;
        return cartItemId == that.cartItemId
                && userId == that.userId
                && quantity == that.quantity
                && Objects.equals(restaurantName, that.restaurantName)
                && Objects.equals(foodName, that.foodName)
                && Objects.equals(priceQuantity, that.priceQuantity);
    }

    /**
     * Returns a hash code value for this CartItemOutDTO.
     * The hash code is generated based on cartItemId, userId, restaurantName, foodName, quantity, and price.
     *
     * @return a hash code value for this CartItemOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, userId, restaurantName, foodName, quantity, priceQuantity);
    }
}
