package com.capstone.users_service.exceptions;

/**
 * Address Not Found Exception.
 */
public class AddressNotFoundException extends RuntimeException {
    /**
     * Address Not Found Exception constructor.
     * @param message error message
     */
    public AddressNotFoundException(String message) {
        super(message);
    }
}
