package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a successful response from an operation.
 * Contains a message indicating the success of the request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSuccessOutDTO {

    /**
     * A message indicating the success of the request.
     * This message can be used to provide feedback or confirmation to the user.
     */
    private String message;

    /**
     * Compares this object with the specified object for equality.
     * Returns {@code true} if the specified object is equal to this object.
     *
     * @param o the object to be compared for equality with this object.
     * @return {@code true} if the specified object is equal to this object; {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestSuccessOutDTO that = (RequestSuccessOutDTO) o;
        return Objects.equals(message, that.message);
    }

    /**
     * Returns a hash code value for this object.
     * This method is supported for the benefit of hash tables.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
