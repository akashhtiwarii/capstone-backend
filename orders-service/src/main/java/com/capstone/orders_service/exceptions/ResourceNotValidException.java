package com.capstone.orders_service.exceptions;

public class ResourceNotValidException extends RuntimeException{
    public ResourceNotValidException(String message) {
        super(message);
    }
}
