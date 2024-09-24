package com.capstone.orders_service.entityTest;

import com.capstone.orders_service.entity.OrderDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderDetailTest {

    @Test
    public void testGetterAndSetter() {
        OrderDetail orderDetail = new OrderDetail();

        assertEquals(0, orderDetail.getOrderDetailId());
        long orderDetailId = 1L;
        orderDetail.setOrderDetailId(orderDetailId);
        assertEquals(orderDetailId, orderDetail.getOrderDetailId());

        assertEquals(0, orderDetail.getOrderId());
        long orderId = 2L;
        orderDetail.setOrderId(orderId);
        assertEquals(orderId, orderDetail.getOrderId());

        assertEquals(0, orderDetail.getFoodId());
        long foodId = 3L;
        orderDetail.setFoodId(foodId);
        assertEquals(foodId, orderDetail.getFoodId());

        assertEquals(0, orderDetail.getQuantity());
        int quantity = 5;
        orderDetail.setQuantity(quantity);
        assertEquals(quantity, orderDetail.getQuantity());

        assertEquals(0.0, orderDetail.getPrice(), 0.0);
        double price = 100.50;
        orderDetail.setPrice(price);
        assertEquals(price, orderDetail.getPrice(), 0.0);
    }

    @Test
    public void testToString() {
        OrderDetail orderDetail = new OrderDetail(1L, 2L, 3L, 5, 100.50);

        String expectedString = "OrderDetail(orderDetailId=1, orderId=2, foodId=3, quantity=5, price=100.5)";
        assertEquals(expectedString, orderDetail.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        OrderDetail orderDetail1 = new OrderDetail(1L, 2L, 3L, 5, 100.50);

        assertEquals(orderDetail1, orderDetail1);
        assertEquals(orderDetail1.hashCode(), orderDetail1.hashCode());

        assertNotEquals(orderDetail1, new Object());

        OrderDetail orderDetail2 = new OrderDetail(1L, 2L, 3L, 5, 100.50);
        assertEquals(orderDetail1, orderDetail2);
        assertEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail2 = new OrderDetail(2L, 2L, 3L, 5, 100.50);
        assertNotEquals(orderDetail1, orderDetail2);
        assertNotEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail2 = new OrderDetail(1L, 3L, 3L, 5, 100.50);
        assertNotEquals(orderDetail1, orderDetail2);
        assertNotEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail2 = new OrderDetail(1L, 2L, 4L, 5, 100.50);
        assertNotEquals(orderDetail1, orderDetail2);
        assertNotEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail2 = new OrderDetail(1L, 2L, 3L, 6, 100.50);
        assertNotEquals(orderDetail1, orderDetail2);
        assertNotEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail2 = new OrderDetail(1L, 2L, 3L, 5, 150.75);
        assertNotEquals(orderDetail1, orderDetail2);
        assertNotEquals(orderDetail1.hashCode(), orderDetail2.hashCode());

        orderDetail1 = new OrderDetail();
        orderDetail2 = new OrderDetail();
        assertEquals(orderDetail1, orderDetail2);
        assertEquals(orderDetail1.hashCode(), orderDetail2.hashCode());
    }
}