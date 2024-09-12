package com.capstone.orders_service.service;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.RestaurantOrderDetailsOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;

import java.util.List;

public interface OrderService {
    List<OrderOutDTO> getOrders(long loggedInUserId, long restaurantId);
    String addOrder(long userId, long addressId);
    String deleteOrder(long orderId);
    String updateOrder(long userId, long orderId, Status status);
    List<RestaurantOrderDetailsOutDTO> getOrderDetails(long restaurantId);
    List<UserOrderDetailsOutDTO> getUserOrders(long userId);
    String cancelOrder(long orderId);
}
