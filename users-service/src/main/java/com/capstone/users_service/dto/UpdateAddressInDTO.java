package com.capstone.users_service.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static com.capstone.users_service.utils.Constants.MAX_PINCODE_VALUE;
import static com.capstone.users_service.utils.Constants.MIN_PINCODE_VALUE;

@Data
public class UpdateAddressInDTO {
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    @Min(value = 1, message = "Valid User ID required")
    private long addressId;
    @NotBlank(message = "Valid address required")
    private String address;
    @NotBlank(message = "Valid city required")
    private String city;
    @Min(value = MIN_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    @Max(value = MAX_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    private long pincode;
    @NotBlank(message = "Valid state required")
    private String state;
}
