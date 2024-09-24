package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for updating a category.
 * <p>
 * This DTO is used to encapsulate the data required to update an existing category in the system.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {

    /**
     * The unique identifier of the user making the update request.
     * <p>
     * This field is used to associate the update action with a specific user.
     * </p>
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The name of the category to be updated.
     * <p>
     * This field specifies the new name of the category and must not be blank.
     * </p>
     */
    @NotBlank(message = "Valid Category Name Required")
    private String name;

    /**
     * Compares this UpdateCategoryDTO object with another object for equality.
     * <p>
     * Two UpdateCategoryDTO objects are considered equal if their userId and name fields are equal.
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
        UpdateCategoryDTO that = (UpdateCategoryDTO) o;
        return userId == that.userId && Objects.equals(name, that.name);
    }

    /**
     * Returns a hash code value for this UpdateCategoryDTO object.
     * <p>
     * The hash code is computed based on the userId and name fields.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, name);
    }
}
