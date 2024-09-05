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
 * Cart Entity.
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    /**
     * cartId to map with cart_id in database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartItemId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "restaurant_id")
    private long restaurantId;
    @Column(name = "food_id")
    private long foodId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;

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

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId, userId, restaurantId, foodId, quantity, price);
    }
}
