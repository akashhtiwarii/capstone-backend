package com.capstone.restaurants_service.dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * DTO To Update Food Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodItemInDTO {

    /**
     * The ID of the user performing the update operation.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long loggedInOwnerId;

    /**
     * The ID of the category to which the food item belongs.
     */
    @NotNull(message = "Valid Category ID required")
    @Min(value = 1, message = "Valid Category ID required")
    private long categoryId;

    /**
     * The name of the food item.
     */
    @NotBlank(message = "Valid Name Required")
    private String name;

    /**
     * The description of the food item.
     */
    private String description;

    /**
     * The price of the food item.
     */
    @NotNull(message = "Valid Price required")
    @Positive(message = "Valid Price required")
    private double price;

    /**
     * Food Item Image.
     */
    private MultipartFile image;

    /**
     * Override Equals.
     * @param o
     * @return Boolean
     */
    @Override
    public boolean equals(Object o) {
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
                && Objects.equals(description, that.description)
                && Objects.equals(image, that.image);
    }

    /**
     * Override Hashcode.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(loggedInOwnerId, categoryId, name, description, price, image);
    }
}
