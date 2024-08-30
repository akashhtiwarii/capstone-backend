package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for Deleting Food Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFoodItemInDTO {
    /**
     * User ID.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    /**
     * Food ID.
     */
    @NotNull(message = "Valid Food ID required")
    @Min(value = 1, message = "Valid Food ID required")
    private long foodId;

    /**
     * Override equals method.
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeleteFoodItemInDTO that = (DeleteFoodItemInDTO) o;
        return userId == that.userId && foodId == that.foodId;
    }
    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, foodId);
    }
}
