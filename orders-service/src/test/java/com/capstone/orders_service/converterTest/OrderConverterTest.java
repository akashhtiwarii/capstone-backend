package com.capstone.orders_service.converterTest;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.converters.OrderConverter;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.entity.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderConverterTest {

    @Test
    void testOrderToOrderOutDTO() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(2L);
        order.setRestaurantId(3L);
        order.setPrice(100.50);
        order.setStatus(Status.PENDING);
        order.setOrderTime(LocalDateTime.of(2024, 9, 12, 10, 0));
        order.setAddressId(4L);

        OrderOutDTO orderOutDTO = OrderConverter.orderToOrderOutDTO(order);

        assertEquals(1L, orderOutDTO.getOrderId());
        assertEquals(2L, orderOutDTO.getUserId());
        assertEquals(3L, orderOutDTO.getRestaurantId());
        assertEquals(100.50, orderOutDTO.getPrice());
        assertEquals(Status.PENDING, orderOutDTO.getStatus());
        assertEquals(LocalDateTime.of(2024, 9, 12, 10, 0), orderOutDTO.getOrderTime());
        assertEquals(4L, orderOutDTO.getAddressId());
    }
}

