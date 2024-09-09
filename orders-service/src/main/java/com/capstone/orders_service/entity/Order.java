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

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "restaurant_id")
    private long restaurantId;
    @Column(name = "price")
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    @Column(name = "address_id")
    private long addressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderId == order.orderId && userId == order.userId
                && restaurantId == order.restaurantId
                && Double.compare(price, order.price) == 0
                && status == order.status && Objects.equals(orderTime, order.orderTime)
                && Objects.equals(addressId, order.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, restaurantId, price, status, orderTime, addressId);
    }
}
