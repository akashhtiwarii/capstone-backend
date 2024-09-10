package com.capstone.orders_service.exceptions;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(final String message) {
        super(message);
    }
}
