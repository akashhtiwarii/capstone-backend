package com.capstone.orders_service.exceptions;

/**
 * Exception thrown when a resource is found to be invalid.
 * <p>
 * This exception is typically used to indicate that a requested resource is
 * not valid or does not meet certain criteria, such as failing validation checks or business rules.
 * </p>
 */
public class ResourceNotValidException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceNotValidException} with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message The detail message explaining the reason for the exception,
     * such as the nature of the validation error.
     */
    public ResourceNotValidException(String message) {
        super(message);
    }
}
