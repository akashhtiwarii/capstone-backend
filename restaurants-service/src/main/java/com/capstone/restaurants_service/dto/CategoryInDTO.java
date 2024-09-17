package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for representing category information when adding or updating a category.
 * This class encapsulates the data required for category operations including user ID, restaurant ID, category name,
 * and an optional image.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInDTO {
    /**
     * The ID of the user associated with the category.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The ID of the restaurant to which the category belongs.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "Valid restaurantId required")
    @Min(value = 1, message = "Valid restaurantId required")
    private long restaurantId;

    /**
     * The name of the category.
     * <p>
     * This field is required and must be a non-blank string that matches the specified pattern:
     * letters, spaces, apostrophes, and hyphens, with a length between 2 and 50 characters.
     * </p>
     */
    @NotBlank(message = "Valid name required")
    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "Valid name required"
    )
    private String name;

    /**
     * Compares this CategoryInDTO object with another object for equality.
     * <p>
     * Two CategoryInDTO objects are considered equal if their userId, restaurantId, name, and image byte arrays are
     * equal.
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
        CategoryInDTO that = (CategoryInDTO) o;
        return userId == that.userId && restaurantId == that.restaurantId
                && Objects.equals(name, that.name);
    }

    /**
     * Returns a hash code value for this CategoryInDTO object.
     * <p>
     * The hash code is computed based on the userId, restaurantId, name, and image fields.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, name);
    }
}
