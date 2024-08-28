package com.capstone.users_service.exceptions;

/**
 * User Not Found Exception.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * User Not Found Exception constructor.
     * @param message error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
