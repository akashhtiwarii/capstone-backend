package com.capstone.restaurants_service.dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantWithImageInDTO {
    /**
     * Restaurant DTO.
     */
    @Valid
    private RestaurantInDTO restaurant;
    /**
     * Image.
     */
    private MultipartFile image;

    /**
     * Override equals.
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
        RestaurantWithImageInDTO that = (RestaurantWithImageInDTO) o;
        return Objects.equals(restaurant, that.restaurant) && Objects.equals(image, that.image);
    }

    /**
     * Override hashcode.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurant, image);
    }
}
