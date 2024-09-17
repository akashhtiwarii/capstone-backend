package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for retrieving a list of restaurants owned by a specific owner.
 * This class contains the owner ID, which is used to fetch the restaurants associated with that owner.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOwnerRestaurantsInDTO {
    /**
     * The ID of the owner whose restaurants are to be retrieved.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "A valid Owner ID Required")
    @Min(value = 1, message = "A valid Owner ID Required")
    private long ownerId;

    /**
     * Compares this GetOwnerRestaurantsInDTO object with another object for equality.
     * <p>
     * Two GetOwnerRestaurantsInDTO objects are considered equal if their ownerId fields are equal.
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
        GetOwnerRestaurantsInDTO that = (GetOwnerRestaurantsInDTO) o;
        return ownerId == that.ownerId;
    }

    /**
     * Returns a hash code value for this GetOwnerRestaurantsInDTO object.
     * <p>
     * The hash code is computed based on the ownerId field.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(ownerId);
    }
}
