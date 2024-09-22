package com.capstone.restaurants_service.exceptions;

/**
 * Exception thrown when a resource already exists in the system.
 * <p>
 * This exception is used to indicate that an attempt was made to create or add a resource
 * that already exists, thus violating a uniqueness constraint.
 * </p>
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceAlreadyExistsException} with the specified detail message.
     * <p>
     * The detail message is a string that describes the reason for the exception, which can be
     * used to provide more information about the error that occurred.
     * </p>
     * @param message the detail message explaining the reason for the exception
     */
    public ResourceAlreadyExistsException(final String message) {
        super(message);
    }
}
