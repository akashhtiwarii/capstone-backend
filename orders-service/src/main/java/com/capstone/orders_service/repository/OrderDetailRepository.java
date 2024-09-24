package com.capstone.orders_service.repository;

import com.capstone.orders_service.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link OrderDetail} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for CRUD operations
 * and custom queries related to {@link OrderDetail} entities.
 * </p>
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    /**
     * Finds all order details associated with a specific order.
     *
     * @param orderId The unique identifier of the order whose details are to be retrieved.
     * @return A {@link List} of {@link OrderDetail} objects associated with the specified order.
     */
    List<OrderDetail> findByOrderId(long orderId);
}
