package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Role;
import lombok.Data;

import java.util.Objects;

/**
 * Data Transfer Object for representing the response after a user login.
 * This class is used to transfer user details from the server to the client after a successful login.
 */
@Data
public class UserOutDTO {

    /**
     * The unique identifier of the logged-in user.
     */
    private long userId;

    /**
     * The email address of the logged-in user.
     */
    private String email;

    /**
     * The name of the logged-in user.
     */
    private String name;

    /**
     * The phone number of the logged-in user.
     */
    private String phone;

    /**
     * The role of the logged-in user, represented as an enumeration of type {@link Role}.
     */
    private Role role;

    /**
     * Compares this UserOutDTO object to the specified object for equality.
     * Two UserOutDTO objects are considered equal if they have the same userId, email, name, phone, and role.
     *
     * @param o the object to compare this UserOutDTO against.
     * @return true if the specified object is equal to this UserOutDTO; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserOutDTO that = (UserOutDTO) o;
        return userId == that.userId && Objects.equals(email, that.email)
                && Objects.equals(name, that.name) && Objects.equals(phone, that.phone)
                && role == that.role;
    }

    /**
     * Returns a hash code value for this UserOutDTO.
     * The hash code is generated based on userId, email, name, phone, and role.
     *
     * @return a hash code value for this UserOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, phone, role);
    }
}
