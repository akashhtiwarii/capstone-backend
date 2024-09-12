package com.capstone.restaurants_service.exceptions;

public class ResourceNotValidException extends RuntimeException{
    public ResourceNotValidException(String message) {
        super(message);
    }
}
