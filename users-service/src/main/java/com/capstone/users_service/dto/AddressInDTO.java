package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.Objects;

import static com.capstone.users_service.utils.Constants.MAX_PINCODE_VALUE;
import static com.capstone.users_service.utils.Constants.MIN_PINCODE_VALUE;

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
    @NotBlank(message = "Email cannot be empty")
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

    /**
     * Override equals method.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressInDTO that = (AddressInDTO) o;
        return pincode == that.pincode && Objects.equals(email, that.email)
                && Objects.equals(address, that.address) && Objects.equals(city, that.city)
                && Objects.equals(state, that.state);
    }

    /**
     * Override hashcode method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, address, pincode, city, state);
    }
}
