package com.capstone.users_service.exceptions;

/**
 * Exception thrown when a requested resource cannot be found.
 * This exception indicates that the resource being queried or accessed does not exist
 * in the system, which can occur during read operations.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     * The detail message is a {@code String} that provides information about the reason
     * for the exception, which can be useful for debugging and logging.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
