package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

import static com.capstone.users_service.utils.Constants.PHONE_NUMBER_LENGTH;

/**
 * Data Transfer Object (DTO) for updating user profile information.
 * This DTO is used to transfer updated profile data from the client to the server.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileInDTO {

    /**
     * The name of the user.
     * Must be a non-blank string and adhere to the specified pattern.
     * Pattern allows alphabets, spaces, apostrophes, and hyphens.
     * The length must be between 2 and 50 characters.
     */
    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "A valid name is mandatory"
    )
    @NotBlank(message = "A valid name is mandatory")
    private String name;

    /**
     * The email address of the user.
     * Must be a non-blank string and adhere to the specified pattern.
     * The pattern restricts the email to the domain 'gmail.com'.
     */
    @Email(message = "Valid Email not found")
    @NotBlank(message = "Valid Email not found")
    @Pattern(
            regexp = "^[\\w.%+-]+@gmail\\.com$",
            message = "Valid Email not found"
    )
    private String email;

    /**
     * The phone number of the user.
     * Must be a non-blank string and match the specified pattern.
     * The pattern ensures that the phone number starts with 9, 8, 7, or 6 and is followed by digits.
     * The length of the phone number is determined by PHONE_NUMBER_LENGTH constant.
     */
    @NotBlank(message = "Phone number should be valid")
    @Pattern(
            regexp = "^[9876]\\d{" + (PHONE_NUMBER_LENGTH - 1) + "}$",
            message = "Phone number should be valid"
    )
    private String phone;

    /**
     * Compares this object with the specified object for equality.
     * Returns {@code true} if the specified object is equal to this object.
     *
     * @param o the object to be compared for equality with this object.
     * @return {@code true} if the specified object is equal to this object; {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateProfileInDTO that = (UpdateProfileInDTO) o;
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone);
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone);
    }
}
