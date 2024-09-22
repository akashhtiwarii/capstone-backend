package com.capstone.orders_service.repository;

import com.capstone.orders_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Order} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for CRUD operations
 * and custom queries related to {@link Order} entities.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds all orders associated with a specific restaurant.
     *
     * @param restaurantId The unique identifier of the restaurant whose orders are to be retrieved.
     * @return A {@link List} of {@link Order} objects associated with the specified restaurant.
     */
    List<Order> findByRestaurantId(long restaurantId);

    /**
     * Finds an order by its unique identifier.
     *
     * @param orderId The unique identifier of the order to be retrieved.
     * @return An {@link Order} object with the specified identifier, or {@code null} if no order is found.
     */
    Order findById(long orderId);

    /**
     * Finds all orders associated with a specific user.
     *
     * @param userId The unique identifier of the user whose orders are to be retrieved.
     * @return A {@link List} of {@link Order} objects associated with the specified user.
     */
    List<Order> findByUserId(long userId);
}
