package com.capstone.orders_service.service;

import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;

import java.util.List;

/**
 * Service interface for managing cart items.
 * <p>
 * This interface defines operations for interacting with the cart, such as adding items,
 * removing items, retrieving items, and updating items in the cart.
 * </p>
 */
public interface CartItemService {

    /**
     * Adds an item to the cart.
     *
     * @param addToCartInDTO An {@link AddToCartInDTO} object containing the details of the item to be added
     * to the cart, including item ID, quantity, and user ID.
     * @return A {@link String} message indicating the result of the operation.
     */
    String addToCart(AddToCartInDTO addToCartInDTO);

    /**
     * Deletes an item from the cart.
     *
     * @param cartItemId The unique identifier of the cart item to be deleted.
     * @return A {@link String} message indicating the result of the operation.
     */
    String deleteCartItem(long cartItemId);

    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId The unique identifier of the user whose cart items are to be retrieved.
     * @return A {@link List} of {@link CartItemOutDTO} objects representing the items in the user's cart.
     */
    List<CartItemOutDTO> getCartItems(long userId);

    /**
     * Updates the quantity of a specific item in the cart.
     *
     * @param cartItemId The unique identifier of the cart item to be updated.
     * @param index The new quantity of the cart item.
     * @return A {@link String} message indicating the result of the operation.
     */
    String updateCartItem(long cartItemId, int index);
}
