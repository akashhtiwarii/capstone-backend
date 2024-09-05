package com.capstone.restaurants_service.dto;

import com.capstone.restaurants_service.ENUM.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Login Response OutDTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDTO {
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
        UserOutDTO that = (UserOutDTO) o;
        return userId == that.userId && Objects.equals(email, that.email)
                && Objects.equals(name, that.name) && Objects.equals(phone, that.phone)
                && role == that.role;
    }

    /**
     * Override hashcode method.
     * @return Hashed Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, phone, role);
    }
}

