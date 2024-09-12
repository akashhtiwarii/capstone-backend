package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for representing detailed information about a user's order.
 * This class is used to transfer order details from the server to the client, including information about the restaurant, food items, order status, and order time.
 */
@Data
public class UserOrderDetailsOutDTO {

    /**
     * The name of the restaurant where the order was placed.
     */
    private String restaurantName;

    private long orderId;

    /**
     * A list of food items included in the order, each represented by a {@link UserFoodItemOutDTO}.
     */
    private List<UserFoodItemOutDTO> foodItemOutDTOS;

    /**
     * The current status of the order, represented as an enumeration of type {@link Status}.
     */
    private Status status;

    /**
     * The date and time when the order was placed.
     */
    private LocalDateTime orderTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrderDetailsOutDTO that = (UserOrderDetailsOutDTO) o;
        return orderId == that.orderId && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(foodItemOutDTOS, that.foodItemOutDTOS) && status == that.status && Objects.equals(orderTime, that.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, orderId, foodItemOutDTOS, status, orderTime);
    }
}
