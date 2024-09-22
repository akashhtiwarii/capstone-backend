package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.OrderDetailOutDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class OrderDetailOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        OrderDetailOutDTO orderDetailOutDTO = new OrderDetailOutDTO();

        assertNull(orderDetailOutDTO.getFoodName());
        String foodName = "Food";
        orderDetailOutDTO.setFoodName(foodName);
        assertEquals(foodName, orderDetailOutDTO.getFoodName());

        assertEquals(0, orderDetailOutDTO.getQuantity());
        int quantity = 3;
        orderDetailOutDTO.setQuantity(quantity);
        assertEquals(quantity, orderDetailOutDTO.getQuantity());

        assertEquals(0.0, orderDetailOutDTO.getPrice());
        double price = 15.99;
        orderDetailOutDTO.setPrice(price);
        assertEquals(price, orderDetailOutDTO.getPrice(), 0.001);
    }

    @Test
    public void testEqualsAndHashcode() {
        String foodName = "Food";
        int quantity = 3;
        double price = 15.99;

        OrderDetailOutDTO orderDetailOutDTO1 = buildOrderDetailOutDTO(foodName, quantity, price);

        assertEquals(orderDetailOutDTO1, orderDetailOutDTO1);
        assertEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO1.hashCode());

        assertNotEquals(orderDetailOutDTO1, new Object());

        OrderDetailOutDTO orderDetailOutDTO2 = buildOrderDetailOutDTO(foodName, quantity, price);
        assertEquals(orderDetailOutDTO1, orderDetailOutDTO2);
        assertEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO2.hashCode());

        orderDetailOutDTO2 = buildOrderDetailOutDTO("Food2", quantity, price);
        assertNotEquals(orderDetailOutDTO1, orderDetailOutDTO2);
        assertNotEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO2.hashCode());

        orderDetailOutDTO2 = buildOrderDetailOutDTO(foodName, quantity + 1, price);
        assertNotEquals(orderDetailOutDTO1, orderDetailOutDTO2);
        assertNotEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO2.hashCode());

        orderDetailOutDTO2 = buildOrderDetailOutDTO(foodName, quantity, price + 1.0);
        assertNotEquals(orderDetailOutDTO1, orderDetailOutDTO2);
        assertNotEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO2.hashCode());

        orderDetailOutDTO1 = new OrderDetailOutDTO();
        orderDetailOutDTO2 = new OrderDetailOutDTO();
        assertEquals(orderDetailOutDTO1, orderDetailOutDTO2);
        assertEquals(orderDetailOutDTO1.hashCode(), orderDetailOutDTO2.hashCode());
    }

    private OrderDetailOutDTO buildOrderDetailOutDTO(String foodName, int quantity, double price) {
        OrderDetailOutDTO orderDetailOutDTO = new OrderDetailOutDTO();

        orderDetailOutDTO.setFoodName(foodName);
        orderDetailOutDTO.setQuantity(quantity);
        orderDetailOutDTO.setPrice(price);

        return orderDetailOutDTO;
    }
}

