package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for representing details of an order from the perspective of a restaurant.
 * This class is used to transfer comprehensive order details,
 * including user information and order specifics, from the server to the client.
 */
@Data
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
     * Constructs a new {@code RestaurantOrderDetailsOutDTO} with the specified values.
     * <p>
     * This constructor initializes the fields of the {@code RestaurantOrderDetailsOutDTO} with the provided values.
     * A new list is created from the given order detail list to ensure that the internal state is not affected by
     * external modifications.
     * </p>
     *
     * @param userId            the unique identifier for the user who placed the order; should not be negative
     * @param userName          the name of the user who placed the order; may be {@code null}
     * @param orderId           the unique identifier for the order; should not be negative
     * @param orderDetailOutDTOS a list of details about the items in the order; may be {@code null}
     * @param status            the current status of the order; should not be {@code null}
     * @param address           the address where the order is to be delivered; may be {@code null}
     */
    public RestaurantOrderDetailsOutDTO(
            final long userId,
            final String userName,
            final long orderId,
            final List<OrderDetailOutDTO> orderDetailOutDTOS,
            final Status status,
            final String address
    ) {
        this.userId = userId;
        this.userName = userName;
        this.orderId = orderId;
        this.orderDetailOutDTOS = orderDetailOutDTOS != null ? new ArrayList<>(orderDetailOutDTOS) : null;
        this.status = status;
        this.address = address;
    }

    /**
     * Sets the list of order details.
     * <p>
     * This method takes a new list and sets it to the internal list, making a copy of it to ensure
     * the internal state cannot be modified externally.
     * </p>
     *
     * @param orderDetailOutDTOS the new list of order details
     */
    public void setOrderDetailOutDTOS(final List<OrderDetailOutDTO> orderDetailOutDTOS) {
        this.orderDetailOutDTOS = orderDetailOutDTOS != null ? new ArrayList<>(orderDetailOutDTOS) : null;
    }

    /**
     * Returns a copy of the list of order details.
     * <p>
     * This method returns a new list that contains the same order details as the internal list.
     * </p>
     *
     * @return a copy of the list of order details
     */
    public List<OrderDetailOutDTO> getOrderDetailOutDTOS() {
        return orderDetailOutDTOS != null ? new ArrayList<>(orderDetailOutDTOS) : null;
    }

    /**
     * Compares this RestaurantOrderDetailsOutDTO object to the specified object for equality.
     * Two RestaurantOrderDetailsOutDTO objects are considered equal
     * if they have the same userId, userName, orderId, orderDetailOutDTOS, status, and address.
     *
     * @param o the object to compare this RestaurantOrderDetailsOutDTO against.
     * @return true if the specified object is equal to this RestaurantOrderDetailsOutDTO; false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderDetailsOutDTO that = (RestaurantOrderDetailsOutDTO) o;
        return userId == that.userId
                && orderId == that.orderId
                && Objects.equals(userName, that.userName)
                && Objects.equals(orderDetailOutDTOS, that.orderDetailOutDTOS)
                && status == that.status && Objects.equals(address, that.address);
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
