package com.capstone.users_service.exceptions;

/**
 * Email already exists exception.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Email already in database exception.
     * @param message error message
     */
    public EmailAlreadyExistsException(final String message) {
        super(message);
    }
}
