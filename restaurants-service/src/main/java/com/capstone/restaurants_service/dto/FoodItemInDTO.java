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
 * Data Transfer Object (DTO) for representing a food item when adding or updating food items in the system.
 * This class contains the necessary fields for food item details including owner ID, category ID, name, description,
 * and price.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemInDTO {
    /**
     * The ID of the logged-in owner performing the operation.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "Valid Owner ID required")
    @Min(value = 1, message = "Valid Owner ID required")
    private long loggedInOwnerId;

    /**
     * The ID of the category to which the food item belongs.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "Valid Category ID required")
    @Min(value = 1, message = "Valid Category ID required")
    private long categoryId;

    /**
     * The name of the food item.
     * <p>
     * This field is required and must be a non-blank string.
     * </p>
     */
    @NotBlank(message = "Valid Name required")
    private String name;

    /**
     * The description of the food item.
     * <p>
     * This field is optional and can be null or empty.
     * </p>
     */
    private String description;

    /**
     * The price of the food item.
     * <p>
     * This field is required and must be a positive number.
     * </p>
     */
    @NotNull(message = "Valid Price required")
    @Positive(message = "Valid Price required")
    private double price;

    /**
     * Compares this FoodItemInDTO object with another object for equality.
     * <p>
     * Two FoodItemInDTO objects are considered equal if their loggedInOwnerId, categoryId, name, description,
     * and price fields are equal.
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
        FoodItemInDTO that = (FoodItemInDTO) o;
        return loggedInOwnerId == that.loggedInOwnerId
                && categoryId == that.categoryId
                && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    /**
     * Returns a hash code value for this FoodItemInDTO object.
     * <p>
     * The hash code is computed based on the loggedInOwnerId, categoryId, name, description, and price fields.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(loggedInOwnerId, categoryId, name, description, price);
    }
}
