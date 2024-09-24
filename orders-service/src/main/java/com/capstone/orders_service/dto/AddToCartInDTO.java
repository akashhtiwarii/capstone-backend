package com.capstone.orders_service.dto;

import com.capstone.orders_service.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * Data Transfer Object for adding an item to the cart.
 * This class is used to transfer cart item details from the client to the server.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddToCartInDTO {

    /**
     * The unique identifier for the user adding the item to the cart.
     * Must be a positive number greater than or equal to 1.
     */
    @NotNull(message = Constants.USER_ID_NOT_VALID)
    @Min(value = 1, message = Constants.USER_ID_NOT_VALID)
    private long userId;

    /**
     * The unique identifier for the restaurant where the food item is available.
     * Must be a positive number greater than or equal to 1.
     */
    @NotNull(message = Constants.RESTAURANT_ID_NOT_VALID)
    @Min(value = 1, message = Constants.RESTAURANT_ID_NOT_VALID)
    private long restaurantId;

    /**
     * The unique identifier for the food item being added to the cart.
     * Must be a positive number greater than or equal to 1.
     */
    @NotNull(message = Constants.FOOD_ID_NOT_VALID)
    @Min(value = 1, message = Constants.FOOD_ID_NOT_VALID)
    private long foodId;

    /**
     * The quantity of the food item to be added to the cart.
     * Must be a positive number greater than 0.
     */
    @NotNull(message = Constants.VALID_QUANTITY_REQUIRED)
    @Positive(message = Constants.VALID_QUANTITY_REQUIRED)
    private int quantity;

    /**
     * Compares this AddToCartInDTO object to the specified object for equality.
     * Two AddToCartInDTO objects are considered equal if they have the same userId, restaurantId, foodId, and quantity.
     *
     * @param o the object to compare this AddToCartInDTO against.
     * @return true if the specified object is equal to this AddToCartInDTO; false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddToCartInDTO that = (AddToCartInDTO) o;
        return userId == that.userId
                && restaurantId == that.restaurantId
                && foodId == that.foodId
                && quantity == that.quantity;
    }

    /**
     * Returns a hash code value for this AddToCartInDTO.
     * The hash code is generated based on userId, restaurantId, foodId, and quantity.
     *
     * @return a hash code value for this AddToCartInDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, foodId, quantity);
    }
}
