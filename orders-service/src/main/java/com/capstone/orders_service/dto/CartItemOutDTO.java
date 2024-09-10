package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemOutDTO {
    private long userId;
    private String restaurantName;
    private String foodName;
    private int quantity;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemOutDTO that = (CartItemOutDTO) o;
        return userId == that.userId && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(foodName, that.foodName) && quantity == that.quantity && Double.compare(price, that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantName, foodName, quantity, price);
    }
}
