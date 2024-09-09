package com.capstone.orders_service.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(final String message) {
        super(message);
    }
}
