package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOrderDetailsOutDTO {
    private long userId;
    private String userName;
    private long orderId;
    private List<OrderDetailOutDTO> orderDetailOutDTOS;
    private Status status;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantOrderDetailsOutDTO that = (RestaurantOrderDetailsOutDTO) o;
        return userId == that.userId && orderId == that.orderId && Objects.equals(userName, that.userName) && Objects.equals(orderDetailOutDTOS, that.orderDetailOutDTOS) && status == that.status && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, orderId, orderDetailOutDTOS, status, address);
    }
}
