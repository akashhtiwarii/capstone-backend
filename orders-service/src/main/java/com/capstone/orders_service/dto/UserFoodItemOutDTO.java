package com.capstone.orders_service.dto;

import lombok.Data;

import java.util.Objects;

/**
 * Data Transfer Object for representing a food item associated with a user.
 * <p>
 * This class is used to transfer details of a food item related to a user, including its ID, name, quantity, and price.
 * It facilitates the communication of food item information from the server to the client.
 * </p>
 */
@Data
public class UserFoodItemOutDTO {

    /**
     * The unique identifier for the food item.
     * <p>
     * This ID is used to uniquely identify the food item in the system.
     * </p>
     */
    private long foodId;

    /**
     * The name of the food item.
     * <p>
     * This field represents the name or title of the food item.
     * </p>
     */
    private String name;

    /**
     * The quantity of the food item.
     * <p>
     * This field indicates how many units of the food item are associated with the user.
     * </p>
     */
    private int quantity;

    /**
     * The price of the food item.
     * <p>
     * This field represents the cost per unit of the food item.
     * </p>
     */
    private double price;

    /**
     * Compares this {@code UserFoodItemOutDTO} instance with another object for equality.
     * <p>
     * Two {@code UserFoodItemOutDTO} instances are considered equal
     * if they have the same food ID, name, quantity, and price.
     * </p>
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
        UserFoodItemOutDTO that = (UserFoodItemOutDTO) o;
        return foodId == that.foodId
                && quantity == that.quantity
                && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name);
    }

    /**
     * Returns a hash code value for this {@code UserFoodItemOutDTO} instance.
     * <p>
     * The hash code is computed based on the food ID, name, quantity, and price to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(foodId, name, quantity, price);
    }
}
