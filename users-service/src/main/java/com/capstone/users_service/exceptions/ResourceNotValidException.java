package com.capstone.users_service.exceptions;

public class ResourceNotValidException extends RuntimeException{
    public ResourceNotValidException(String message) {
        super(message);
    }
}
