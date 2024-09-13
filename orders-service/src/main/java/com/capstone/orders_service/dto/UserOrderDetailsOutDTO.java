package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for representing detailed information about a user's order.
 * <p>
 * This class is used to transfer comprehensive details about an order from the server to the client.
 * It includes information about the restaurant, the list of food items in the order,
 * the order status, and the time when the order was placed.
 * </p>
 */
@Data
public class UserOrderDetailsOutDTO {

    /**
     * The name of the restaurant where the order was placed.
     * <p>
     * This field represents the name of the restaurant associated with the order.
     * </p>
     */
    private String restaurantName;

    /**
     * The email address of the restaurant where the order was placed.
     * <p>
     * This field contains the contact email of the restaurant associated with the order.
     * </p>
     */
    private String restaurantEmail;

    /**
     * The unique identifier for the order.
     * <p>
     * This field uniquely identifies the order within the system.
     * </p>
     */
    private long orderId;

    /**
     * A list of food items included in the order, each represented by a {@link UserFoodItemOutDTO}.
     * <p>
     * This field contains the details of each food item ordered, including their names, quantities, and prices.
     * </p>
     */
    private List<UserFoodItemOutDTO> foodItemOutDTOS;

    /**
     * The current status of the order, represented as an enumeration of type {@link Status}.
     * <p>
     * This field indicates the current state of the order, such as pending, completed, or canceled.
     * </p>
     */
    private Status status;

    /**
     * The date and time when the order was placed.
     * <p>
     * This field records the timestamp of when the order was created.
     * </p>
     */
    private LocalDateTime orderTime;

    /**
     * Compares this {@code UserOrderDetailsOutDTO} instance with another object for equality.
     * <p>
     * Two {@code UserOrderDetailsOutDTO} instances are considered equal
     * if they have the same order ID, restaurant name, list of food items, status, and order time.
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
        UserOrderDetailsOutDTO that = (UserOrderDetailsOutDTO) o;
        return orderId == that.orderId
                && Objects.equals(restaurantName, that.restaurantName)
                && Objects.equals(foodItemOutDTOS, that.foodItemOutDTOS)
                && status == that.status
                && Objects.equals(orderTime, that.orderTime);
    }

    /**
     * Returns a hash code value for this {@code UserOrderDetailsOutDTO} instance.
     * <p>
     * The hash code is computed based on the restaurant name, order ID, list of food items, status, and order time
     * to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, orderId, foodItemOutDTOS, status, orderTime);
    }
}
