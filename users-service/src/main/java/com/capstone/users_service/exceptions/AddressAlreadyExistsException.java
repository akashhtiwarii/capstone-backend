package com.capstone.users_service.exceptions;

public class AddressAlreadyExistsException extends RuntimeException{
    public AddressAlreadyExistsException(final String message) {
        super(message);
    }
}
