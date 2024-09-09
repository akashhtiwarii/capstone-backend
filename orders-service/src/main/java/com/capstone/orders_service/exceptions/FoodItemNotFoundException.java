package com.capstone.orders_service.exceptions;

public class FoodItemNotFoundException extends RuntimeException{
    public FoodItemNotFoundException(final String message) {
        super(message);
    }
}
