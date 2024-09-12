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
 * Represents an item in the cart.
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    /**
     * Unique identifier for the cart item.
     * Mapped to the "cart_id" column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartItemId;

    /**
     * Identifier of the user who owns the cart item.
     * Mapped to the "user_id" column in the database.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * Identifier of the restaurant associated with the cart item.
     * Mapped to the "restaurant_id" column in the database.
     */
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * Identifier of the food item in the cart.
     * Mapped to the "food_id" column in the database.
     */
    @Column(name = "food_id")
    private long foodId;

    /**
     * Quantity of the food item in the cart.
     * Mapped to the "quantity" column in the database.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Price of the food item in the cart.
     * Mapped to the "price" column in the database.
     */
    @Column(name = "price")
    private double price;

    /**
     * Compares this cart item to another object.
     * Two cart items are considered equal if they have the same cartItemId, userId, restaurantId, foodId, quantity, and price.
     *
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
        return cartItemId == cartItem.cartItemId && userId == cartItem.userId && restaurantId == cartItem.restaurantId && foodId == cartItem.foodId && quantity == cartItem.quantity && Double.compare(price, cartItem.price) == 0;
    }

    /**
     * Returns a hash code value for this cart item.
     * The hash code is computed based on cartItemId, userId, restaurantId, foodId, quantity, and price.
     *
     * @return The hash code value for this cart item.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, userId, restaurantId, foodId, quantity, price);
    }
}
