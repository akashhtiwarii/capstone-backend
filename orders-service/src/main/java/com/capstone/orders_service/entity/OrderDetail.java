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
 * Represents the details of an order.
 */
@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    /**
     * Unique identifier for the order detail.
     * Mapped to the "order_details_id" column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private long orderDetailId;

    /**
     * Identifier of the order to which this detail belongs.
     * Mapped to the "order_id" column in the database.
     */
    @Column(name = "order_id")
    private long orderId;

    /**
     * Identifier of the food item in the order detail.
     * Mapped to the "food_id" column in the database.
     */
    @Column(name = "food_id")
    private long foodId;

    /**
     * Quantity of the food item in the order detail.
     * Mapped to the "quantity" column in the database.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Price of the food item in the order detail.
     * Mapped to the "price" column in the database.
     */
    @Column(name = "price")
    private double price;

    /**
     * Compares this order detail to another object.
     * Two order details are considered equal if they have the same orderDetailId, orderId, foodId, quantity, and price.
     *
     * @param o The object to compare with.
     * @return true if this order detail is equal to the specified object; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetail that = (OrderDetail) o;
        return orderDetailId == that.orderDetailId && orderId == that.orderId && foodId == that.foodId && quantity == that.quantity && Double.compare(price, that.price) == 0;
    }

    /**
     * Returns a hash code value for this order detail.
     * The hash code is computed based on orderDetailId, orderId, foodId, quantity, and price.
     *
     * @return The hash code value for this order detail.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderDetailId, orderId, foodId, quantity, price);
    }
}
