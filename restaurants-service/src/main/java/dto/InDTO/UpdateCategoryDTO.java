package dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO TO update Category.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {
    /**
     * User ID.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    /**
     * Category Name.
     */
    @NotBlank(message = "Valid Category Name Required")
    private String name;
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
        UpdateCategoryDTO that = (UpdateCategoryDTO) o;
        return userId == that.userId && Objects.equals(name, that.name);
    }
    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, name);
    }
}
