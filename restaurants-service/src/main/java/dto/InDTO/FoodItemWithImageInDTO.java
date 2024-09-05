package dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemWithImageInDTO {
    /**
     * FoodItemInDTO.
     */
    @Valid
    private FoodItemInDTO foodItem;
    /**
     * Image.
     */
    private MultipartFile image;
}
