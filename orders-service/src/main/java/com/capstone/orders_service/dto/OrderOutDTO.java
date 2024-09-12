package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for representing an order.
 * This class is used to transfer order details from the server to the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutDTO {

    /**
     * The unique identifier for the order.
     */
    private long orderId;

    /**
     * The unique identifier for the user who placed the order.
     */
    private long userId;

    /**
     * The unique identifier for the restaurant where the order was placed.
     */
    private long restaurantId;

    /**
     * The total price of the order.
     */
    private double price;

    /**
     * The current status of the order, represented as an enumeration.
     */
    private Status status;

    /**
     * The time when the order was placed.
     */
    private LocalDateTime orderTime;

    /**
     * The unique identifier for the address where the order is to be delivered.
     */
    private long addressId;

    /**
     * Compares this OrderOutDTO object to the specified object for equality.
     * Two OrderOutDTO objects are considered equal if they have the same orderId, userId, restaurantId, price, status, orderTime, and addressId.
     *
     * @param o the object to compare this OrderOutDTO against.
     * @return true if the specified object is equal to this OrderOutDTO; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderOutDTO that = (OrderOutDTO) o;
        return orderId == that.orderId && userId == that.userId
                && restaurantId == that.restaurantId
                && Double.compare(price, that.price) == 0
                && status == that.status && Objects.equals(orderTime, that.orderTime)
                && Objects.equals(addressId, that.addressId);
    }

    /**
     * Returns a hash code value for this OrderOutDTO.
     * The hash code is generated based on orderId, userId, restaurantId, price, status, orderTime, and addressId.
     *
     * @return a hash code value for this OrderOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, restaurantId, price, status, orderTime, addressId);
    }
}
