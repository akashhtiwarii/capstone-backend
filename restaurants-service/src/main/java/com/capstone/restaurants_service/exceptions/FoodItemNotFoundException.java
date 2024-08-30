package com.capstone.restaurants_service.exceptions;

/**
 * Food Item Not Found Exception.
 */
public class FoodItemNotFoundException extends RuntimeException {
    /**
     * Food Not found in database.
     * @param message
     */
    public FoodItemNotFoundException(final String message) {
        super(message);
    }
}
