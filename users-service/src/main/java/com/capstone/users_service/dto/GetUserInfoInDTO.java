package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for retrieving user information.
 * This DTO is used to encapsulate the user IDs needed to fetch information about a specific user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoInDTO {

    /**
     * The ID of the user whose information is being requested.
     * This field must be a positive value and is required to identify the user in question.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The ID of the user who is currently logged in and making the request.
     * This field must be a positive value and is used to ensure that the request is being made by an authorized user.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long loggedInUserId;

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetUserInfoInDTO that = (GetUserInfoInDTO) o;
        return userId == that.userId && loggedInUserId == that.loggedInUserId;
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, loggedInUserId);
    }
}
