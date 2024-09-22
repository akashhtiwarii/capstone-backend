package com.capstone.orders_service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) that represents a list of cart items along with the total amount.
 */
@Data
public class CartItemsListOutDTO {

    /**
     * List of cart item DTOs.
     * Each item in this list represents an individual cart item with details such as item ID, name, quantity, and price.
     */
    private List<CartItemOutDTO> cartItemOutDTOList;

    /**
     * The total amount for all items in the cart.
     * This value is calculated as the sum of the prices of all cart items multiplied by their respective quantities.
     */
    private double totalAmount;

    /**
     * Returns a defensive copy of the cart item list to prevent exposing internal representation.
     *
     * @return a copy of the cart item list
     */
    public List<CartItemOutDTO> getCartItemOutDTOList() {
        return cartItemOutDTOList == null ? new ArrayList<>() : new ArrayList<>(cartItemOutDTOList);
    }

    /**
     * Sets the cart item list by making a defensive copy of the input list to avoid storing an externally mutable object.
     *
     * @param cartItemOutDTOList the cart item list to set
     */
    public void setCartItemOutDTOList(final List<CartItemOutDTO> cartItemOutDTOList) {
        this.cartItemOutDTOList = cartItemOutDTOList == null ? new ArrayList<>() : new ArrayList<>(cartItemOutDTOList);
    }

    /**
     * Compares this CartItemsListOutDTO to the specified object.
     * The result is true if and only if the argument is not null and is a
     * CartItemsListOutDTO object that represents the same data as this object.
     *
     * @param o the object to compare this CartItemsListOutDTO against
     * @return true if the given object is equal to this CartItemsListOutDTO, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItemsListOutDTO that = (CartItemsListOutDTO) o;
        return Double.compare(that.totalAmount, totalAmount) == 0
                && Objects.equals(getCartItemOutDTOList(), that.getCartItemOutDTOList());
    }

    /**
     * Returns a hash code value for this CartItemsListOutDTO.
     * The hash code is computed based on the cartItemOutDTOList and totalAmount.
     *
     * @return a hash code value for this CartItemsListOutDTO
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCartItemOutDTOList(), totalAmount);
    }
}
