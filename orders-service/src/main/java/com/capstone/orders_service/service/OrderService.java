package com.capstone.orders_service.service;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.OrderDetailsOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;

import java.util.List;

public interface OrderService {
    List<OrderOutDTO> getOrders(long loggedInUserId, long restaurantId);
    String addOrder(long userId);
    String deleteOrder(long orderId);
    String updateOrder(long userId, long orderId, Status status);
    List<OrderDetailsOutDTO> getOrderDetails(long restaurantId);
}
