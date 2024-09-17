package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object for representing a success response message.
 * <p>
 * This class is used to encapsulate a success message that is returned in response to a successful request.
 * It provides a simple structure to communicate the outcome of an operation.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSuccessOutDTO {

    /**
     * The success message associated with the response.
     * <p>
     * This message provides information about the successful completion of a request or operation.
     * </p>
     */
    private String message;

    /**
     * Compares this {@code RequestSuccessOutDTO} instance with another object for equality.
     * <p>
     * Two {@code RequestSuccessOutDTO} instances are considered equal if they have the same success message.
     * </p>
     *
     * @param o the object to be compared for equality with this instance
     * @return {@code true} if this instance is equal to the specified object; {@code false} otherwise
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
     * Returns a hash code value for this {@code RequestSuccessOutDTO} instance.
     * <p>
     * The hash code is computed based on the success message to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
