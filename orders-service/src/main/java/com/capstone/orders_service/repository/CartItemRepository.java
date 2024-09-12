package com.capstone.orders_service.repository;

import com.capstone.orders_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(long userId);
    List<CartItem> findByRestaurantIdAndUserId(long restaurantId, long userId);
    CartItem findByRestaurantIdAndUserIdAndFoodId(long restaurantId, long userId, long foodId);
    CartItem findById(long cartId);
}
