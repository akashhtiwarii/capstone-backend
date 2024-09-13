package com.capstone.restaurants_service.exceptions;

/**
 * Exception thrown when a requested resource is not found in the system.
 * <p>
 * This exception is used to indicate that an operation could not be completed
 * because the specified resource does not exist.
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceNotFoundException} with the specified detail message.
     * <p>
     * The detail message provides information about the resource that was not found,
     * which can be useful for debugging or user feedback.
     * </p>
     * @param message the detail message explaining why the resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
