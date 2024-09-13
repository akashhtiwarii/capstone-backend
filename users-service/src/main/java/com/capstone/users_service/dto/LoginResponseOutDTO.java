package com.capstone.users_service.dto;

import com.capstone.users_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) that represents the response sent after a successful user login.
 * This DTO provides detailed information about the logged-in user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseOutDTO {

    /**
     * The unique identifier of the logged-in user.
     */
    private long userId;

    /**
     * The email address of the logged-in user.
     */
    private String email;

    /**
     * The full name of the logged-in user.
     */
    private String name;

    /**
     * The phone number of the logged-in user.
     */
    private String phone;

    /**
     * The role assigned to the logged-in user.
     */
    private Role role;

    /**
     * A message indicating the authentication status or additional information.
     */
    private String message;

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
        LoginResponseOutDTO that = (LoginResponseOutDTO) o;
        return userId == that.userId && Objects.equals(email, that.email)
                && Objects.equals(name, that.name) && Objects.equals(phone, that.phone)
                && role == that.role && Objects.equals(message, that.message);
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, phone, role, message);
    }
}
