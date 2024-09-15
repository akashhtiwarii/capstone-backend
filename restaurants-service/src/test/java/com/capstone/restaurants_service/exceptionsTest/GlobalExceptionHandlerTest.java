package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid message");
        ResponseEntity<Map<String, String>> response = handler.handleHttpMessageNotReadableException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().get("status"));
        assertEquals("Invalid Request. Try Again!", response.getBody().get("message"));
    }

    @Test
    public void testHandleResourceAlreadyExistsException() {
        ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException("Resource already exists");
        ResponseEntity<ErrorResponse> response = handler.handleResourceAlreadyExistsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Resource already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleResourceNotValidException() {
        ResourceNotValidException ex = new ResourceNotValidException("Resource not valid");
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotValidException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(401, response.getBody().getStatus());
        assertEquals("Resource not valid", response.getBody().getMessage());
    }

    @Test
    public void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("fieldName");
        Map<String, String> errors = handler.handleMethodArgumentTypeMismatchException(ex);

        assertEquals("Invalid Data Provided", errors.get("fieldName"));
    }

    @Test
    public void testHandleValidationException() {
        FieldError fieldError = new FieldError("objectName", "field", "error message");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(java.util.Collections.singletonList(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        Map<String, String> errors = handler.handleValidationException(ex);

        assertEquals("error message", errors.get("field"));
    }

    @Test
    public void testHandleBindException() {
        FieldError fieldError = new FieldError("objectName", "field", "error message");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(java.util.Collections.singletonList(fieldError));
        BindException ex = new BindException(bindingResult);

        Map<String, String> errors = handler.handleBindException(ex);

        assertEquals("error message", errors.get("field"));
    }

    @Test
    public void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime exception");
        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Runtime exception", response.getBody().getMessage());
    }

    @Test
    public void testHandleGeneralException() {
        Exception ex = new Exception("General exception");
        ResponseEntity<ErrorResponse> response = handler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("General exception", response.getBody().getMessage());
    }
}