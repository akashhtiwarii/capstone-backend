package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object for representing details of a food item within an order.
 * <p>
 * This class is used to encapsulate and transfer details about individual food items included in an order.
 * It provides information about the food item's name, quantity, and price.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailOutDTO {

    /**
     * The name of the food item included in the order.
     * <p>
     * This represents the name of the food item as specified in the order details.
     * </p>
     */
    private String foodName;

    /**
     * The quantity of the food item ordered.
     * <p>
     * This indicates how many units of the specified food item are included in the order.
     * </p>
     */
    private int quantity;

    /**
     * The price of a single unit of the food item.
     * <p>
     * This represents the cost of one unit of the food item, which may be used to calculate the total cost
     * of the food item in the order.
     * </p>
     */
    private double price;

    /**
     * Compares this {@code OrderDetailOutDTO} instance with another object for equality.
     * <p>
     * Two {@code OrderDetailOutDTO} instances are considered equal if they have the same food name, quantity,
     * and price.
     * </p>
     *
     * @param o the object to be compared for equality with this instance
     * @return {@code true} if this instance is equal to the specified object; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetailOutDTO that = (OrderDetailOutDTO) o;
        return quantity == that.quantity
                && Double.compare(price, that.price) == 0
                && Objects.equals(foodName, that.foodName);
    }

    /**
     * Returns a hash code value for this {@code OrderDetailOutDTO} instance.
     * <p>
     * The hash code is computed based on the food name, quantity, and price to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(foodName, quantity, price);
    }
}
