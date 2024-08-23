package com.capstone.users_service.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Email already in database exception.
     * @param message error message
     */
    public EmailAlreadyExistsException(final String message) {
        super(message);
    }
}
