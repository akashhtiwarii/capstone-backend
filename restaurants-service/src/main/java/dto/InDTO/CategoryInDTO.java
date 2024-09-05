package dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.Objects;

/**
 * Restaurant In DTO to add Restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInDTO {
    /**
     * User Id of user.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    /**
     * restaurantId to map with restaurantId field of entity.
     */
    @NotNull(message = "Valid restaurantId required")
    @Min(value = 1, message = "Valid restaurantId required")
    private long restaurantId;
    /**
     * name to map with name field of entity.
     */
    @NotBlank(message = "Valid name required")
    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "Valid name required"
    )
    private String name;
    /**
     * Image Bytes.
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
        CategoryInDTO that = (CategoryInDTO) o;
        return userId == that.userId && restaurantId == that.restaurantId
                && Objects.equals(name, that.name) && Objects.deepEquals(image, that.image);
    }

    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, name, Arrays.hashCode(image));
    }
}
