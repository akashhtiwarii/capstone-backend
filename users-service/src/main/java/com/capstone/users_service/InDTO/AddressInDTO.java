package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

import static com.capstone.users_service.utils.Constants.*;

/**
 * AddressInDTO for adding new address.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInDTO {
    /**
     * email of the user whose address is to be added.
     */
    @Email(message = "No valid email found")
    private String email;
    /**
     * address of the user whose address is to be added.
     */
    @NotBlank(message = "Address cannot be empty")
    private String address;
    /**
     * pincode of the user whose address is to be added.
     */
    @Min(value = MIN_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    @Max(value = MAX_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    private long pincode;
    /**
     * city of the user whose address is to be added.
     */
    @NotBlank(message = "City cannot be empty")
    private String city;
    /**
     * state of the user whose address is to be added.
     */
    @NotBlank(message = "State cannot be empty")
    private String state;
}
