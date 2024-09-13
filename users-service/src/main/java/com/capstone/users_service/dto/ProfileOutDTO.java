package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) that represents the profile information of a user.
 * This DTO includes basic user details and wallet amount.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileOutDTO {

    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The phone number of the user.
     */
    private String phone;

    /**
     * The current amount of money in the user's wallet.
     */
    private double walletAmount;

    /**
     * Compares this object with the specified object for equality.
     * Returns {@code true} if the specified object is equal to this object.
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
        ProfileOutDTO that = (ProfileOutDTO) o;
        return Double.compare(that.walletAmount, walletAmount) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone);
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone, walletAmount);
    }
}
