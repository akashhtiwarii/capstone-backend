package com.capstone.orders_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Represents an error response containing details about an error that occurred in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /**
     * The HTTP status code associated with the error.
     */
    private int status;

    /**
     * A descriptive message explaining the nature of the error.
     * Provides additional details or context about the error.
     */
    private String message;

    /**
     * Checks if this {@code ErrorResponse} is equal to another object.
     * Two {@code ErrorResponse}
     * instances are considered equal if they have the same {@code status} and {@code message}.
     * @param o the object to compare with
     * @return {@code true} if this {@code ErrorResponse} is equal to the specified object; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse that = (ErrorResponse) o;
        return status == that.status && Objects.equals(message, that.message);
    }

    /**
     * Returns a hash code value for this {@code ErrorResponse}.
     * The hash code is computed based on the {@code status} and {@code message} fields.
     *
     * @return a hash code value for this {@code ErrorResponse}
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}

