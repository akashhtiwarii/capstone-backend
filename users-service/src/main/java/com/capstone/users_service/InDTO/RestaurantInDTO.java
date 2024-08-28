package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.capstone.users_service.utils.Constants.PHONE_NUMBER_LENGTH;

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
     * image to link image of Restaurant entity.
     */
    private byte[] image;
}
