package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutDTO {
    private long orderId;
    private long userId;
    private long restaurantId;
    private double price;
    private Status status;
    private LocalDateTime orderTime;
    private long addressId;

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

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, restaurantId, price, status, orderTime, addressId);
    }
}
