package com.capstone.users_service.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

import static com.capstone.users_service.utils.Constants.MAX_PINCODE_VALUE;
import static com.capstone.users_service.utils.Constants.MIN_PINCODE_VALUE;

/**
 * Data Transfer Object (DTO) for updating an existing address.
 * This DTO is used to transfer address update information from the client to the server.
 */
@Data
public class UpdateAddressInDTO {

    /**
     * The ID of the user whose address is being updated.
     * Must be a positive number.
     */
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The ID of the address being updated.
     * Must be a positive number.
     */
    @Min(value = 1, message = "Valid Address ID required")
    private long addressId;

    /**
     * The new address details.
     * Must not be empty.
     */
    @NotBlank(message = "Valid address required")
    private String address;

    /**
     * The city of the address being updated.
     * Must not be empty.
     */
    @NotBlank(message = "Valid city required")
    private String city;

    /**
     * The postal code of the address being updated.
     * Must be a 6-digit number, constrained by the defined minimum and maximum values.
     */
    @Min(value = MIN_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    @Max(value = MAX_PINCODE_VALUE, message = "Pincode must be a 6-digit number")
    private long pincode;

    /**
     * The state of the address being updated.
     * Must not be empty.
     */
    @NotBlank(message = "Valid state required")
    private String state;

    /**
     * Compares this object with the specified object for equality.
     * Returns {@code true} if the specified object is equal to this object.
     *
     * @param o the object to be compared for equality with this object.
     * @return {@code true} if the specified object is equal to this object; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateAddressInDTO that = (UpdateAddressInDTO) o;
        return userId == that.userId && addressId == that.addressId && pincode == that.pincode
                && Objects.equals(address, that.address) && Objects.equals(city, that.city)
                && Objects.equals(state, that.state);
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, addressId, address, city, pincode, state);
    }
}
