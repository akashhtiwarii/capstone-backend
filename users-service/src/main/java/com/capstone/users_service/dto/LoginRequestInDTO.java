package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * LoginRequestInDTO for taking email and password at login time.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestInDTO {
    /**
     * email ID of user.
     */
    @Email(message = "Valid email required")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    /**
     * password of user.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;

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
        LoginRequestInDTO that = (LoginRequestInDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    /**
     * Override hashcode method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
