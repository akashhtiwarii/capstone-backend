package com.capstone.orders_service.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(final String message) {
        super(message);
    }
}
