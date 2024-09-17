package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) for representing a successful request response.
 * <p>
 * This class is used to encapsulate a success message that is returned to the client upon
 * the successful execution of an operation.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSuccessOutDTO {
    /**
     * A message indicating the success of the request.
     * <p>
     * This field contains a descriptive message that provides information about the successful
     * completion of the request.
     * </p>
     */
    private String message;

    /**
     * Compares this RequestSuccessOutDTO object with another object for equality.
     * <p>
     * Two RequestSuccessOutDTO objects are considered equal if their message fields are equal.
     * </p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
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
     * Returns a hash code value for this RequestSuccessOutDTO object.
     * <p>
     * The hash code is computed based on the message field.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
