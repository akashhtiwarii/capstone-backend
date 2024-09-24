package com.capstone.orders_service.repository;

import com.capstone.orders_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link CartItem} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for CRUD operations
 * and custom queries related to {@link CartItem} entities.
 * </p>
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Finds all cart items associated with a specific user.
     *
     * @param userId The unique identifier of the user whose cart items are to be retrieved.
     * @return A {@link List} of {@link CartItem} objects associated with the specified user.
     */
    List<CartItem> findByUserId(long userId);

    /**
     * Finds all cart items associated with a specific restaurant and user.
     *
     * @param restaurantId The unique identifier of the restaurant.
     * @param userId The unique identifier of the user.
     * @return A {@link List} of {@link CartItem} objects associated with the specified restaurant and user.
     */
    List<CartItem> findByRestaurantIdAndUserId(long restaurantId, long userId);

    /**
     * Finds a specific cart item by its associated restaurant, user, and food identifiers.
     *
     * @param restaurantId The unique identifier of the restaurant.
     * @param userId The unique identifier of the user.
     * @param foodId The unique identifier of the food item.
     * @return A {@link CartItem} object matching the specified identifiers, or {@code null} if no match is found.
     */
    CartItem findByRestaurantIdAndUserIdAndFoodId(long restaurantId, long userId, long foodId);

    /**
     * Finds a cart item by its unique identifier.
     *
     * @param cartId The unique identifier of the cart item to be retrieved.
     * @return A {@link CartItem} object matching the specified identifier, or {@code null} if no match is found.
     */
    CartItem findById(long cartId);
}
