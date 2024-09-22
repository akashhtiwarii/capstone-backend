package com.capstone.orders_service.exceptions;

/**
 * Exception thrown when there is a conflict related to a restaurant.
 */
public class RestaurantConflictException extends RuntimeException {

    /**
     * Constructs a new {@link RestaurantConflictException} with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message The detail message explaining the reason for the conflict,
     * such as the nature of the conflicting data or the business rule violation.
     */
    public RestaurantConflictException(final String message) {
        super(message);
    }
}
