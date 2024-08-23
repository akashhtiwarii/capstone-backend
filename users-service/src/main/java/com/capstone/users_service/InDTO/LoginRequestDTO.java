package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    /**
     * email ID of user.
     */
    @Email(message = "Valid email required")
    private String email;
    /**
     * password of user.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
