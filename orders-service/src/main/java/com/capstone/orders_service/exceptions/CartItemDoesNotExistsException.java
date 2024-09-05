package com.capstone.orders_service.exceptions;

public class CartItemDoesNotExistsException extends RuntimeException{
    public CartItemDoesNotExistsException(final String message) {
        super(message);
    }
}
