package com.capstone.orders_service.converters;

import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.entity.Order;

/**
 * This class provides methods to convert entity objects to data transfer objects (DTOs) for order operations.
 */
public final class OrderConverter {

    /**
     * Private constructor for CategoryConverters.
     */
    private OrderConverter() {
        throw new UnsupportedOperationException("Utility Class Cannot be instantiated");
    }

    /**
     * Converts an {@link Order} entity to an {@link OrderOutDTO} data transfer object.
     *
     * @param order the entity containing order details to be converted.
     * @return an {@link OrderOutDTO} populated with the values from the provided entity.
     */
    public static OrderOutDTO orderToOrderOutDTO(final Order order) {
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
