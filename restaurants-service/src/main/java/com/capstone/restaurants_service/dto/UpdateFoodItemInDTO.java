package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for updating an existing food item.
 * <p>
 * This DTO encapsulates the data required to update a food item, including its category, name, description, and price.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodItemInDTO {

    /**
     * The ID of the user performing the update operation.
     * <p>
     * This field links the update action to the specific user who is making the changes.
     * </p>
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long loggedInOwnerId;

    /**
     * The ID of the category to which the food item belongs.
     * <p>
     * This field associates the food item with a specific category.
     * </p>
     */
    @NotNull(message = "Valid Category ID required")
    @Min(value = 1, message = "Valid Category ID required")
    private long categoryId;

    /**
     * The name of the food item.
     * <p>
     * This field specifies the name of the food item and must not be blank.
     * </p>
     */
    @NotBlank(message = "Valid Name Required")
    private String name;

    /**
     * The description of the food item.
     * <p>
     * This field provides additional details about the food item. It can be null.
     * </p>
     */
    private String description;

    /**
     * The price of the food item.
     * <p>
     * This field specifies the cost of the food item and must be a positive value.
     * </p>
     */
    @NotNull(message = "Valid Price required")
    @Positive(message = "Valid Price required")
    private double price;

    /**
     * Compares this UpdateFoodItemInDTO object with another object for equality.
     * <p>
     * Two UpdateFoodItemInDTO objects are considered equal if their loggedInOwnerId, categoryId, name,
     * description, and price fields are equal.
     * </p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateFoodItemInDTO that = (UpdateFoodItemInDTO) o;
        return loggedInOwnerId == that.loggedInOwnerId
                && categoryId == that.categoryId
                && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    /**
     * Returns a hash code value for this UpdateFoodItemInDTO object.
     * <p>
     * The hash code is computed based on the fields: loggedInOwnerId, categoryId, name, description, and price.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(loggedInOwnerId, categoryId, name, description, price);
    }
}
