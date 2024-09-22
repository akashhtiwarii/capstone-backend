package com.capstone.users_service.exceptions;

/**
 * Exception thrown when a resource is found to be invalid.
 * This exception indicates that the resource does not meet the required criteria
 * or constraints, which may occur during validation processes.
 */
public class ResourceNotValidException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotValidException} with the specified detail message.
     * The detail message is a {@code String} that provides information about why
     * the resource is considered invalid, which can be useful for debugging and error handling.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public ResourceNotValidException(final String message) {
        super(message);
    }
}
