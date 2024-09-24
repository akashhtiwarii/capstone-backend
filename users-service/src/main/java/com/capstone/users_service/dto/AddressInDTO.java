package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

import static com.capstone.users_service.utils.Constants.MAX_PINCODE_VALUE;
import static com.capstone.users_service.utils.Constants.MIN_PINCODE_VALUE;

/**
 * Data Transfer Object (DTO) for adding a new address.
 * Contains information related to a user's address including user ID, address details, and validation constraints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInDTO {

    /**
     * ID of the user to whom the address belongs.
     * Must be a positive value.
     */
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * Address line of the user.
     * Cannot be empty.
     */
    @NotBlank(message = "Address cannot be empty")
    private String address;

    /**
     * Pincode for the address.
     * Must be a 6-digit number within the range defined by
     * {@link com.capstone.users_service.utils.Constants#MIN_PINCODE_VALUE}
     * and {@link com.capstone.users_service.utils.Constants#MAX_PINCODE_VALUE}.
     */
    @Min(value = MIN_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    @Max(value = MAX_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    private long pincode;

    /**
     * City where the address is located.
     * Cannot be empty.
     */
    @NotBlank(message = "City cannot be empty")
    private String city;

    /**
     * State where the address is located.
     * Cannot be empty.
     */
    @NotBlank(message = "State cannot be empty")
    private String state;

    /**
     * Compares this AddressInDTO to another object.
     * Two AddressInDTO objects are considered equal if all their fields are the same.
     *
     * @param o The object to compare this AddressInDTO with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressInDTO that = (AddressInDTO) o;
        return userId == that.userId
                && pincode == that.pincode
                && Objects.equals(address, that.address)
                && Objects.equals(city, that.city)
                && Objects.equals(state, that.state);
    }

    /**
     * Returns a hash code value for this AddressInDTO.
     * The hash code is computed based on all fields of the AddressInDTO.
     *
     * @return The hash code value for this AddressInDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, address, pincode, city, state);
    }
}
