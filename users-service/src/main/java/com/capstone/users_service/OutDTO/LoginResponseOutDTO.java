package com.capstone.users_service.OutDTO;

import com.capstone.users_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * address of logged-in user.
     */
    private String address;
    /**
     * role of logged-in user.
     */
    private Role role;
    /**
     * Authentication status message.
     */
    private String message;
}
