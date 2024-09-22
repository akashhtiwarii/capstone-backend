package com.capstone.orders_service.service;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.RestaurantOrderDetailsOutDTO;
import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;

import java.util.List;

/**
 * Service interface for managing orders within the system.
 * <p>
 * This interface defines operations for retrieving, adding, deleting, updating, and canceling orders.
 * It includes methods for interacting with both user-specific and restaurant-specific order details.
 * </p>
 */
public interface OrderService {

    /**
     * Retrieves a list of orders for a specific user and restaurant.
     *
     * @param loggedInUserId The unique identifier of the logged-in user requesting the orders.
     * @param restaurantId The unique identifier of the restaurant whose orders are to be retrieved.
     * @return A {@link List} of {@link OrderOutDTO} objects representing the orders.
     */
    List<OrderOutDTO> getOrders(long loggedInUserId, long restaurantId);

    /**
     * Adds a new order for a specific user.
     *
     * @param userId The unique identifier of the user placing the order.
     * @param addressId The unique identifier of the address where the order will be delivered.
     * @return A {@link String} message indicating the result of the operation.
     */
    String addOrder(long userId, long addressId);

    /**
     * Deletes an existing order.
     *
     * @param orderId The unique identifier of the order to be deleted.
     * @return A {@link String} message indicating the result of the operation.
     */
    String deleteOrder(long orderId);

    /**
     * Updates the status of an existing order.
     *
     * @param userId The unique identifier of the user making the update.
     * @param orderId The unique identifier of the order to be updated.
     * @param status The new status to be set for the order, represented by the {@link Status} enum.
     * @return A {@link String} message indicating the result of the operation.
     */
    String updateOrder(long userId, long orderId, Status status);

    /**
     * Retrieves detailed information about orders for a specific restaurant.
     *
     * @param restaurantId The unique identifier of the restaurant whose order details are to be retrieved.
     * @return A {@link List} of {@link RestaurantOrderDetailsOutDTO} objects representing the order details.
     */
    List<RestaurantOrderDetailsOutDTO> getOrderDetails(long restaurantId);

    /**
     * Retrieves a list of orders for a specific user.
     *
     * @param userId The unique identifier of the user whose orders are to be retrieved.
     * @return A {@link List} of {@link UserOrderDetailsOutDTO} objects representing the user's orders.
     */
    List<UserOrderDetailsOutDTO> getUserOrders(long userId);

    /**
     * Cancels an existing order.
     *
     * @param orderId The unique identifier of the order to be canceled.
     * @return A {@link String} message indicating the result of the operation.
     */
    String cancelOrder(long orderId);
}
