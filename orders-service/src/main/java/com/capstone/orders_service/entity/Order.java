package com.capstone.orders_service.entity;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an order in the system, mapped to the 'orders' table in the database.
 * <p>
 * This class captures the details of an order placed by a user, including identifiers, price, status,
 * and the time when the order was placed.
 * </p>
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Unique identifier for the order.
     * <p>
     * This field is mapped to the 'order_id' column in the database.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    /**
     * Identifier of the user who placed the order.
     * <p>
     * This field is mapped to the 'user_id' column in the database.
     * </p>
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * Identifier of the restaurant associated with the order.
     * <p>
     * This field is mapped to the 'restaurant_id' column in the database.
     * </p>
     */
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * Total price of the order.
     * <p>
     * This field is mapped to the 'price' column in the database.
     * </p>
     */
    @Column(name = "price")
    private double price;

    /**
     * Status of the order.
     * <p>
     * This field is mapped to the 'status' column in the database and uses the {@link Status} enumeration to represent
     * the current state of the order.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    /**
     * Time when the order was placed.
     * <p>
     * This field is mapped to the 'order_time' column in the database.
     * </p>
     */
    @Column(name = "order_time")
    private LocalDateTime orderTime;

    /**
     * Identifier of the address associated with the order.
     * <p>
     * This field is mapped to the 'address_id' column in the database.
     * </p>
     */
    @Column(name = "address_id")
    private long addressId;

    /**
     * Compares this order to another object for equality.
     * <p>
     * Two orders are considered equal if they have the same orderId, userId, restaurantId, price, status,
     * orderTime, and addressId.
     * </p>
     *
     * @param o The object to compare with.
     * @return true if this order is equal to the specified object; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderId == order.orderId
                && userId == order.userId
                && restaurantId == order.restaurantId
                && Double.compare(price, order.price) == 0
                && status == order.status
                && Objects.equals(orderTime, order.orderTime)
                && Objects.equals(addressId, order.addressId);
    }

    /**
     * Returns a hash code value for this order.
     * <p>
     * The hash code is computed based on orderId, userId, restaurantId, price, status, orderTime, and addressId.
     * </p>
     *
     * @return The hash code value for this order.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, restaurantId, price, status, orderTime, addressId);
    }
}
