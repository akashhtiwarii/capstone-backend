package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.Objects;

/**
 * DTO for Adding Food Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemInDTO {
    /**
     * categoryId to map with categoryId field of entity.
     */
    @NotNull(message = "Valid Category ID required")
    @Min(value = 1, message = "Valid Category ID required")
    private long categoryId;
    /**
     * name to map with name field of entity.
     */
    @NotBlank(message = "Valid Name required")
    private String name;
    /**
     * description to map with description field of entity.
     */
    private String description;
    /**
     * price to map with price field of entity.
     */
    @NotNull(message = "Valid Price required")
    @Positive(message = "Valid Price required")
    private double price;
    /**
     * image to map with image field of entity.
     */
    private byte[] image;

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
        FoodItemInDTO that = (FoodItemInDTO) o;
        return categoryId == that.categoryId && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.deepEquals(image, that.image);
    }

    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, description, price, Arrays.hashCode(image));
    }
}
