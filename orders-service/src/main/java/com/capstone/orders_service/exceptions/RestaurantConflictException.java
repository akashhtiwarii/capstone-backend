package com.capstone.orders_service.exceptions;

public class RestaurantConflictException extends RuntimeException {
    public RestaurantConflictException(final String message) {
        super(message);
    }
}
