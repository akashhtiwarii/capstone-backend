package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
}
