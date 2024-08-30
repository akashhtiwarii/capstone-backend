package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object for deleting a category.
 * This DTO contains the information needed to delete a category
 * in the restaurant service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCategoryInDTO {

    /**
     * The ID of the user who is requesting the category deletion.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The ID of the category to be deleted.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long categoryId;

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
        DeleteCategoryInDTO that = (DeleteCategoryInDTO) o;
        return userId == that.userId && categoryId == that.categoryId;
    }

    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }
}
