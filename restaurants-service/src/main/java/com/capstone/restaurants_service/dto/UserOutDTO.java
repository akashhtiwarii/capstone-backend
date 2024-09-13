package com.capstone.restaurants_service.dto;

import com.capstone.restaurants_service.ENUM.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for representing the response of a user login operation.
 * <p>
 * This DTO encapsulates the details of a logged-in user, including the user ID, email, name, phone number, and role.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDTO {

    /**
     * The unique identifier of the logged-in user.
     * <p>
     * This field represents the user ID assigned to the logged-in user.
     * </p>
     */
    private long userId;

    /**
     * The email address of the logged-in user.
     * <p>
     * This field contains the email address associated with the logged-in user.
     * </p>
     */
    private String email;

    /**
     * The name of the logged-in user.
     * <p>
     * This field holds the name of the user who is logged in.
     * </p>
     */
    private String name;

    /**
     * The phone number of the logged-in user.
     * <p>
     * This field contains the phone number of the user who is logged in.
     * </p>
     */
    private String phone;

    /**
     * The role of the logged-in user.
     * <p>
     * This field specifies the role assigned to the logged-in user, which is represented by an enum.
     * </p>
     */
    private Role role;

    /**
     * Compares this UserOutDTO object with another object for equality.
     * <p>
     * Two UserOutDTO objects are considered equal if their userId, email, name, phone, and role fields are equal.
     * </p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
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
        return userId == that.userId
                && Objects.equals(email, that.email)
                && Objects.equals(name, that.name)
                && Objects.equals(phone, that.phone)
                && role == that.role;
    }

    /**
     * Returns a hash code value for this UserOutDTO object.
     * <p>
     * The hash code is computed based on the fields: userId, email, name, phone, and role.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, phone, role);
    }
}
