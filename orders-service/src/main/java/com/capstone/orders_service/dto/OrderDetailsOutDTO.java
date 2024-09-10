package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsOutDTO {
    private long userId;
    private List<OrderDetail> orderDetailList;
    private Status status;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsOutDTO that = (OrderDetailsOutDTO) o;
        return userId == that.userId && Objects.equals(orderDetailList, that.orderDetailList) && status == that.status && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderDetailList, status, address);
    }
}
