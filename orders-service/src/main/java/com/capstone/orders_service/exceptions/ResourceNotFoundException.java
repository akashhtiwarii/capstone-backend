package com.capstone.orders_service.exceptions;

/**
 * Exception thrown when a requested resource is not found.
 * <p>
 * This exception is typically used to indicate that a specific resource, such as an order, user, or food item,
 * could not be located in the system.
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceNotFoundException} with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message The detail message explaining the reason for the exception,
     * such as the name of the missing resource.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
