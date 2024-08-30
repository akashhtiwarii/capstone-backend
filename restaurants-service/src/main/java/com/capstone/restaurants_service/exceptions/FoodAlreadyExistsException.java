package com.capstone.restaurants_service.exceptions;

/**
 * Food already exists exception.
 */
public class FoodAlreadyExistsException extends RuntimeException {
    /**
     * Food already in database exception.
     * @param message error message
     */
    public FoodAlreadyExistsException(final String message) {
        super(message);
    }
}
