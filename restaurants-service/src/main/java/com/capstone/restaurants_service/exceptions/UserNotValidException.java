package com.capstone.restaurants_service.exceptions;

/**
 * User Not Valid Exception.
 */
public class UserNotValidException extends RuntimeException {

    /**
     * User Not Found Exception constructor.
     * @param message error message
     */
    public UserNotValidException(String message) {
        super(message);
    }
}
