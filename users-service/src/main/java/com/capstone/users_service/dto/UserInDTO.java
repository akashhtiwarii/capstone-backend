package com.capstone.users_service.dto;

import com.capstone.users_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.capstone.users_service.utils.Constants.MIN_PASSWORD_LENGTH;
import static com.capstone.users_service.utils.Constants.PHONE_NUMBER_LENGTH;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * This DTO is used to encapsulate user details for user registration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInDTO {

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
     * The password for the user.
     * The password length should be at least 8 characters.
     */
    @NotBlank(message = "Password should be minimum 8 characters")
    @Size(min = MIN_PASSWORD_LENGTH, message = "Password should be minimum 8 characters")
    private String password;

    /**
     * The email address of the user.
     * Must be a non-blank string, adhere to email format, and be limited to the 'gmail.com' domain.
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
     */
    @NotBlank(message = "Phone number should be valid")
    @Pattern(
            regexp = "^[9876]\\d{" + (PHONE_NUMBER_LENGTH - 1) + "}$",
            message = "Phone number should be valid"
    )
    private String phone;

    /**
     * The role assigned to the user.
     * Must be a non-null value representing the user's role, e.g., USER or OWNER.
     */
    @NotNull(message = "Role is mandatory")
    private Role role;

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
        UserInDTO that = (UserInDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password)
                && Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
                && role == that.role;
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, password, email, phone, role);
    }
}
