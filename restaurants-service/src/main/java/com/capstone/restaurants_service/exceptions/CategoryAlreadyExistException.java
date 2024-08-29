package com.capstone.restaurants_service.exceptions;

/**
 * Category Already Exists Exception.
 */
public class CategoryAlreadyExistException extends RuntimeException {
    /**
     * Category Already present in the database.
     * @param message error message
     */
    public CategoryAlreadyExistException(final String message) {
        super(message);
    }
}
