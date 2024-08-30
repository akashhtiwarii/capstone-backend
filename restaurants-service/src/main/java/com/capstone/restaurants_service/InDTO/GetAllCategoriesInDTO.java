package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO To Get All Categories.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoriesInDTO {
    /**
     * Restaurant ID.
     */
    @NotNull(message = "Valid Restaurant ID required")
    @Min(value = 1, message = "Valid Restaurant ID required")
    private long restaurantId;
    /**
     * User ID.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
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
        GetAllCategoriesInDTO that = (GetAllCategoriesInDTO) o;
        return restaurantId == that.restaurantId && userId == that.userId;
    }
    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, userId);
    }
}
