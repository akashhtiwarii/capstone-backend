package com.capstone.orders_service.exceptions;

/**
 * Exception thrown when an operation cannot be completed due to insufficient funds.
 * <p>
 * This exception is typically used to indicate that a user does not have enough money in their wallet
 * to complete a transaction or an order.
 * </p>
 */
public class InsufficientAmountException extends RuntimeException {

    /**
     * Constructs a new {@link InsufficientAmountException} with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InsufficientAmountException(final String message) {
        super(message);
    }
}
