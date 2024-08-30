package com.capstone.restaurants_service.exceptions;

/**
 * Invalid Category Exception.
 */
public class InvalidCategoryException extends RuntimeException {
    /**
     * Category Invalid.
     * @param message
     */
    public InvalidCategoryException(final String message) {
        super(message);
    }
}
