package com.capstone.users_service.dto;

import com.capstone.users_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * LoginResponseOutDTO to giver user details after login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseOutDTO {
    /**
     * userId of logged-in user.
     */
    private long userId;
    /**
     * email of logged-in user.
     */
    private String email;
    /**
     * name of logged-in user.
     */
    private String name;
    /**
     * phone of logged-in user.
     */
    private String phone;

    /**
     * role of logged-in user.
     */
    private Role role;
    /**
     * Authentication status message.
     */
    private String message;

    /**
     * Override equals method.
     * @param o
     * @return
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
     * Override hashcode method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, phone, role, message);
    }
}
