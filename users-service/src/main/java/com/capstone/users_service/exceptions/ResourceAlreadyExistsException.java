package com.capstone.users_service.exceptions;

/**
 * Exception thrown when an attempt is made to create a resource that already exists.
 * This exception is used to indicate that the resource being created is already present
 * in the system, thereby preventing duplicate entries.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with the specified detail message.
     * The detail message is a {@code String} that describes the cause of the exception.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public ResourceAlreadyExistsException(final String message) {
        super(message);
    }
}
