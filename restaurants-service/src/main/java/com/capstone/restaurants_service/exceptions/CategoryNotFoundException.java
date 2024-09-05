package com.capstone.restaurants_service.exceptions;

/**
 * Category Not Found Exception.
 */
public class CategoryNotFoundException extends RuntimeException {
    /**
     * No Category Found in database exception.
     * @param message error message
     */
    public CategoryNotFoundException(final String message) {
        super(message);
    }
}
