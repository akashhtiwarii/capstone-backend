package com.capstone.orders_service.converters;

import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.entity.Order;

public class OrderConverter {
    public static OrderOutDTO orderToOrderOutDTO(Order order) {
        OrderOutDTO orderOutDTO = new OrderOutDTO();
        orderOutDTO.setOrderId(order.getOrderId());
        orderOutDTO.setUserId(order.getUserId());
        orderOutDTO.setRestaurantId(order.getRestaurantId());
        orderOutDTO.setPrice(order.getPrice());
        orderOutDTO.setStatus(order.getStatus());
        orderOutDTO.setOrderTime(order.getOrderTime());
        orderOutDTO.setAddressId(order.getAddressId());
        return orderOutDTO;
    }
}
