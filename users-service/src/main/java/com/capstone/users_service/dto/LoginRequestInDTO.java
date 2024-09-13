package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for login requests.
 * This DTO encapsulates the email and password required for user authentication during login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestInDTO {

    /**
     * The email ID of the user attempting to log in.
     * This field must be a valid email format and cannot be empty.
     */
    @Email(message = "Valid email required")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    /**
     * The password of the user attempting to log in.
     * This field cannot be empty.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;

    /**
     * Indicates whether some other object is "equal to" this one.
     * Compares this object with another object to determine if they are equal.
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginRequestInDTO that = (LoginRequestInDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
