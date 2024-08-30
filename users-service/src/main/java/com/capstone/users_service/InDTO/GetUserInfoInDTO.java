package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Get User Info IN DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoInDTO {
    /**
     * User ID.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    /**
     * User ID of Logged In User.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long loggedInUserId;
}
