package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for requesting addresses associated with a user by email.
 * This DTO contains the user's email which is used to fetch all associated addresses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestInDTO {

    /**
     * Email address of the user.
     * This field is used to identify the user for whom addresses need to be retrieved.
     * The email must be a valid email format.
     */
    @Email(message = "No valid email found")
    private String email;

    /**
     * Compares this AddressRequestInDTO to another object.
     * Two AddressRequestInDTO objects are considered equal if their email fields are the same.
     *
     * @param o The object to compare this AddressRequestInDTO with.
     * @return {@code true} if the objects are equal based on the email field, {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressRequestInDTO that = (AddressRequestInDTO) o;
        return Objects.equals(email, that.email);
    }

    /**
     * Returns a hash code value for this AddressRequestInDTO.
     * The hash code is computed based on the email field.
     *
     * @return The hash code value for this AddressRequestInDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
