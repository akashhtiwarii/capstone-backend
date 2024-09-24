package com.capstone.orders_service.entityTest;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.entity.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testGetterAndSetter() {
        Order order = new Order();

        assertEquals(0, order.getOrderId());
        long orderId = 1L;
        order.setOrderId(orderId);
        assertEquals(orderId, order.getOrderId());

        assertEquals(0, order.getUserId());
        long userId = 2L;
        order.setUserId(userId);
        assertEquals(userId, order.getUserId());

        assertEquals(0, order.getRestaurantId());
        long restaurantId = 3L;
        order.setRestaurantId(restaurantId);
        assertEquals(restaurantId, order.getRestaurantId());

        assertEquals(0.0, order.getPrice(), 0.0);
        double price = 150.75;
        order.setPrice(price);
        assertEquals(price, order.getPrice(), 0.0);

        assertNull(order.getStatus());
        Status status = Status.PENDING;
        order.setStatus(status);
        assertEquals(status, order.getStatus());

        assertNull(order.getOrderTime());
        LocalDateTime orderTime = LocalDateTime.now();
        order.setOrderTime(orderTime);
        assertEquals(orderTime, order.getOrderTime());

        assertEquals(0, order.getAddressId());
        long addressId = 5L;
        order.setAddressId(addressId);
        assertEquals(addressId, order.getAddressId());
    }

    @Test
    public void testToString() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 9, 10, 12, 30);

        Order order = new Order(1L, 2L, 3L, 150.75, Status.PENDING, orderTime, 5L);

        String expectedString = "Order(orderId=1, userId=2, restaurantId=3, price=150.75, status=PENDING, orderTime=2024-09-10T12:30, addressId=5)";
        assertEquals(expectedString, order.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        LocalDateTime orderTime = LocalDateTime.of(2024, 9, 10, 12, 30);

        Order order1 = buildOrder(1L, 2L, 3L, 150.75, Status.PENDING, orderTime, 5L);

        assertEquals(order1, order1);
        assertEquals(order1.hashCode(), order1.hashCode());

        assertNotEquals(order1, new Object());

        Order order2 = buildOrder(1L, 2L, 3L, 150.75, Status.PENDING, orderTime, 5L);
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(2L, 2L, 3L, 150.75, Status.PENDING, orderTime, 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 3L, 3L, 150.75, Status.PENDING, orderTime, 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 2L, 4L, 150.75, Status.PENDING, orderTime, 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 2L, 3L, 200.75, Status.PENDING, orderTime, 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 2L, 3L, 150.75, Status.COMPLETED, orderTime, 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 2L, 3L, 150.75, Status.PENDING, orderTime.plusDays(1), 5L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order2 = buildOrder(1L, 2L, 3L, 150.75, Status.PENDING, orderTime, 6L);
        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());

        order1 = new Order();
        order2 = new Order();
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    private Order buildOrder(long orderId, long userId, long restaurantId, double price, Status status, LocalDateTime orderTime, long addressId) {
        return new Order(orderId, userId, restaurantId, price, status, orderTime, addressId);
    }
}
