package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.OrderOutDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        OrderOutDTO orderOutDTO = new OrderOutDTO();

        assertEquals(0, orderOutDTO.getOrderId());
        long orderId = 12345L;
        orderOutDTO.setOrderId(orderId);
        assertEquals(orderId, orderOutDTO.getOrderId());

        assertEquals(0, orderOutDTO.getUserId());
        long userId = 67890L;
        orderOutDTO.setUserId(userId);
        assertEquals(userId, orderOutDTO.getUserId());

        assertEquals(0, orderOutDTO.getRestaurantId());
        long restaurantId = 54321L;
        orderOutDTO.setRestaurantId(restaurantId);
        assertEquals(restaurantId, orderOutDTO.getRestaurantId());

        assertEquals(0.0, orderOutDTO.getPrice());
        double price = 99.99;
        orderOutDTO.setPrice(price);
        assertEquals(price, orderOutDTO.getPrice(), 0.001);

        assertNull(orderOutDTO.getStatus());
        // Assuming Status is an enum, replace with appropriate enum value
        Status status = Status.PENDING;
        orderOutDTO.setStatus(status);
        assertEquals(status, orderOutDTO.getStatus());

        assertNull(orderOutDTO.getOrderTime());
        LocalDateTime orderTime = LocalDateTime.now();
        orderOutDTO.setOrderTime(orderTime);
        assertEquals(orderTime, orderOutDTO.getOrderTime());

        assertEquals(0, orderOutDTO.getAddressId());
        long addressId = 98765L;
        orderOutDTO.setAddressId(addressId);
        assertEquals(addressId, orderOutDTO.getAddressId());
    }

    @Test
    public void testEqualsAndHashcode() {
        long orderId = 12345L;
        long userId = 67890L;
        long restaurantId = 54321L;
        double price = 99.99;
        Status status = Status.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();
        long addressId = 98765L;

        OrderOutDTO orderOutDTO1 = buildOrderOutDTO(orderId, userId, restaurantId, price, status, orderTime, addressId);

        assertEquals(orderOutDTO1, orderOutDTO1);
        assertEquals(orderOutDTO1.hashCode(), orderOutDTO1.hashCode());

        assertNotEquals(orderOutDTO1, new Object());

        OrderOutDTO orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId, price, status, orderTime, addressId);
        assertEquals(orderOutDTO1, orderOutDTO2);
        assertEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId + 1, userId, restaurantId, price, status, orderTime, addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId + 1, restaurantId, price, status, orderTime, addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId + 1, price, status, orderTime, addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId, price + 1.0, status, orderTime, addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId, price, Status.COMPLETED, orderTime, addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId, price, status, orderTime.plusDays(1), addressId);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO2 = buildOrderOutDTO(orderId, userId, restaurantId, price, status, orderTime, addressId + 1);
        assertNotEquals(orderOutDTO1, orderOutDTO2);
        assertNotEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());

        orderOutDTO1 = new OrderOutDTO();
        orderOutDTO2 = new OrderOutDTO();
        assertEquals(orderOutDTO1, orderOutDTO2);
        assertEquals(orderOutDTO1.hashCode(), orderOutDTO2.hashCode());
    }

    private OrderOutDTO buildOrderOutDTO(long orderId, long userId, long restaurantId, double price, Status status, LocalDateTime orderTime, long addressId) {
        OrderOutDTO orderOutDTO = new OrderOutDTO();

        orderOutDTO.setOrderId(orderId);
        orderOutDTO.setUserId(userId);
        orderOutDTO.setRestaurantId(restaurantId);
        orderOutDTO.setPrice(price);
        orderOutDTO.setStatus(status);
        orderOutDTO.setOrderTime(orderTime);
        orderOutDTO.setAddressId(addressId);

        return orderOutDTO;
    }
}
