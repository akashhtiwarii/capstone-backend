package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleConstraintViolationException() {
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleConstraintViolationException(ex);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid Request. Try Again!", response.getBody().get("message"));
    }

    @Test
    void handleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleHttpMessageNotReadableException(ex);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid Request. Try Again!", response.getBody().get("message"));
    }

    @Test
    void handleResourceAlreadyExistsException() {
        ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException("Resource already exists");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceAlreadyExistsException(ex);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Resource already exists", response.getBody().getMessage());
    }

    @Test
    void handleResourceNotValidException() {
        ResourceNotValidException ex = new ResourceNotValidException("Invalid resource");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotValidException(ex);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid resource", response.getBody().getMessage());
    }

    @Test
    void handleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void handleRuntimeException() {
        RuntimeException ex = new RuntimeException("Unexpected error");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(ex);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }

    @Test
    void handleGeneralException() {
        Exception ex = new Exception("General exception");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(ex);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("General exception", response.getBody().getMessage());
    }
}
