package dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * DTO for Adding Food Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemInDTO {
    /**
     * Logged in Owner Info for validation.
     */
    @NotNull(message = "Valid Owner ID required")
    @Min(value = 1, message = "Valid Owner ID required")
    private long loggedInOwnerId;
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
        return loggedInOwnerId == that.loggedInOwnerId
                && categoryId == that.categoryId
                && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(loggedInOwnerId, categoryId, name, description, price);
    }
}
