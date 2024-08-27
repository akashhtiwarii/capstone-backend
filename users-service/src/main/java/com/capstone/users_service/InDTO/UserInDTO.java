package com.capstone.users_service.InDTO;

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
 * UserInDTO for adding new user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInDTO {
    /**
     * name for linking name field from User Entity.
     */
    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "A valid name is mandatory"
    )
    @NotBlank(message = "A valid name is mandatory")
    private String name;
    /**
     * password for linking password field from User Entity.
     */
    @NotBlank(message = "Password is mandatory")
    @Size(min = MIN_PASSWORD_LENGTH, message = "Password should be minimum 8 characters")
    private String password;

    /**
     * email for linking email field from User Entity.
     */
    @Email(message = "Valid Email not found")
    @NotBlank(message = "Email is mandatory")
    private String email;
    /**
     * phone for linking phone field from User Entity.
     */
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(
            regexp = "^\\d{" + PHONE_NUMBER_LENGTH + "}$",
            message = "Phone number should be valid"
    )
    private String phone;
    /**
     * role for linking role field from User Entity.
     */
    @NotNull(message = "Role is mandatory")
    private Role role;

    /**
     * Override equals method for testing.
     * @param o object
     * @return true or false based on conditions
     */
    @Override
    public boolean equals(Object o) {
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
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, password, email, phone, role);
    }
}
