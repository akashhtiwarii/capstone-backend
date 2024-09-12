package com.capstone.orders_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Represents an item in the cart, mapped to the 'cart' table in the database.
 * <p>
 * This class is used to persist and retrieve cart item information, including details such as cart item ID,
 * user ID, restaurant ID, food item ID, quantity, and price of the item in the cart.
 * </p>
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    /**
     * Unique identifier for the cart item.
     * <p>
     * This field is mapped to the 'cart_id' column in the database.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartItemId;

    /**
     * Identifier of the user who owns the cart item.
     * <p>
     * This field is mapped to the 'user_id' column in the database.
     * </p>
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * Identifier of the restaurant associated with the cart item.
     * <p>
     * This field is mapped to the 'restaurant_id' column in the database.
     * </p>
     */
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * Identifier of the food item in the cart.
     * <p>
     * This field is mapped to the 'food_id' column in the database.
     * </p>
     */
    @Column(name = "food_id")
    private long foodId;

    /**
     * Quantity of the food item in the cart.
     * <p>
     * This field is mapped to the 'quantity' column in the database.
     * </p>
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Price of the food item in the cart.
     * <p>
     * This field is mapped to the 'price' column in the database.
     * </p>
     */
    @Column(name = "price")
    private double price;

    /**
     * Compares this cart item to another object for equality.
     * <p>
     * Two cart items are considered equal if they have the same cartItemId, userId, restaurantId, foodId,
     * quantity, and price.
     * </p>
     * @param o The object to compare with.
     * @return true if this cart item is equal to the specified object; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return cartItemId == cartItem.cartItemId
                && userId == cartItem.userId
                && restaurantId == cartItem.restaurantId
                && foodId == cartItem.foodId
                && quantity == cartItem.quantity
                && Double.compare(price, cartItem.price) == 0;
    }

    /**
     * Returns a hash code value for this cart item.
     * <p>
     * The hash code is computed based on cartItemId, userId, restaurantId, foodId, quantity, and price.
     * </p>
     * @return The hash code value for this cart item.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, userId, restaurantId, foodId, quantity, price);
    }
}
