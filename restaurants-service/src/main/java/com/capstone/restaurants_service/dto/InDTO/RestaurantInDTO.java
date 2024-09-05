package com.capstone.restaurants_service.dto.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Objects;

import static com.capstone.restaurants_service.utils.Constants.PHONE_NUMBER_LENGTH;

/**
 * Restaurant In DTO to add Restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInDTO {

    /**
     * ownerId to link ownerId of Restaurant entity.
     */
    @NotNull(message = "Valid OwnerID required")
    @Min(value = 1, message = "Valid OwnerID required")
    private long ownerId;

    /**
     * name to link name of Restaurant entity.
     */
    @NotBlank(message = "Enter a valid name for restaurant")
    private String name;

    /**
     * email to link email of Restaurant entity.
     */
    @Email(message = "Enter a valid email ID for restaurant")
    @NotBlank(message = "Enter a valid email ID for restaurant")
    @Pattern(
            regexp = "^[\\w.%+-]+@gmail\\.com$",
            message = "Enter a valid email ID for restaurant"
    )
    private String email;

    /**
     * phone to link phone of Restaurant entity.
     */
    @NotBlank(message = "Phone number should be valid")
    @Pattern(
            regexp = "^[9876]\\d{" + (PHONE_NUMBER_LENGTH - 1) + "}$",
            message = "Phone number should be valid"
    )
    private String phone;

    /**
     * address to link address of Restaurant entity.
     */
    @NotBlank(message = "Address for restaurant cannot be empty")
    private String address;
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
        RestaurantInDTO that = (RestaurantInDTO) o;
        return ownerId == that.ownerId && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
                && Objects.equals(address, that.address);
    }
    /**
     * Override hashcode method.
     * @return Hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(ownerId, name, email, phone, address);
    }
}
