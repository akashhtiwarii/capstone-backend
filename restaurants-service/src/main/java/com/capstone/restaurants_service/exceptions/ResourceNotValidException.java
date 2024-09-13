package com.capstone.restaurants_service.exceptions;

/**
 * Exception thrown when a resource does not meet the required validation criteria.
 * <p>
 * This exception is used to indicate that a resource failed validation checks,
 * meaning the data provided is not valid according to the application's rules.
 * </p>
 */
public class ResourceNotValidException extends RuntimeException {

    /**
     * Constructs a new {@link ResourceNotValidException} with the specified detail message.
     * <p>
     * The detail message provides information about why the resource is considered invalid,
     * which can be useful for debugging or informing the user of what went wrong.
     * </p>
     * @param message the detail message explaining why the resource is not valid
     */
    public ResourceNotValidException(String message) {
        super(message);
    }
}
