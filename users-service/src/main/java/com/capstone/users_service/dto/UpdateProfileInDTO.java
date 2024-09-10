package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

import static com.capstone.users_service.utils.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileInDTO {
    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "A valid name is mandatory"
    )
    @NotBlank(message = "A valid name is mandatory")
    private String name;

    @Email(message = "Valid Email not found")
    @NotBlank(message = "Valid Email not found")
    @Pattern(
            regexp = "^[\\w.%+-]+@gmail\\.com$",
            message = "Valid Email not found"
    )
    private String email;

    @NotBlank(message = "Phone number should be valid")
    @Pattern(
            regexp = "^[9876]\\d{" + (PHONE_NUMBER_LENGTH - 1) + "}$",
            message = "Phone number should be valid"
    )
    private String phone;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @Min(value = MIN_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    @Max(value = MAX_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    private long pincode;

    @NotBlank(message = "State cannot be empty")
    private String state;
}
