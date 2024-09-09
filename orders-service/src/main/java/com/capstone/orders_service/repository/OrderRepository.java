package com.capstone.orders_service.repository;

import com.capstone.orders_service.dto.OrderOutDTO;
import com.capstone.orders_service.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurantId(long restaurantId);
    Order findById(long orderId);
}
