package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for representing details of an order from the perspective of a restaurant.
 * This class is used to transfer comprehensive order details, including user information and order specifics, from the server to the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOrderDetailsOutDTO {

    /**
     * The unique identifier for the user who placed the order.
     */
    private long userId;

    /**
     * The name of the user who placed the order.
     */
    private String userName;

    /**
     * The unique identifier for the order.
     */
    private long orderId;

    /**
     * A list of details about the items in the order.
     */
    private List<OrderDetailOutDTO> orderDetailOutDTOS;

    /**
     * The current status of the order, represented as an enumeration.
     */
    private Status status;

    /**
     * The address where the order is to be delivered.
     */
    private String address;

    /**
     * Compares this RestaurantOrderDetailsOutDTO object to the specified object for equality.
     * Two RestaurantOrderDetailsOutDTO objects are considered equal if they have the same userId, userName, orderId, orderDetailOutDTOS, status, and address.
     *
     * @param o the object to compare this RestaurantOrderDetailsOutDTO against.
     * @return true if the specified object is equal to this RestaurantOrderDetailsOutDTO; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderDetailsOutDTO that = (RestaurantOrderDetailsOutDTO) o;
        return userId == that.userId && orderId == that.orderId && Objects.equals(userName, that.userName) && Objects.equals(orderDetailOutDTOS, that.orderDetailOutDTOS) && status == that.status && Objects.equals(address, that.address);
    }

    /**
     * Returns a hash code value for this RestaurantOrderDetailsOutDTO.
     * The hash code is generated based on userId, userName, orderId, orderDetailOutDTOS, status, and address.
     *
     * @return a hash code value for this RestaurantOrderDetailsOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, orderId, orderDetailOutDTOS, status, address);
    }
}
