package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("400", responseEntity.getBody().get("status"));
        assertEquals("Invalid Request. Try Again!", responseEntity.getBody().get("message"));
    }

    @Test
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler.handleHttpMessageNotReadableException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("400", responseEntity.getBody().get("status"));
        assertEquals("Invalid Request. Try Again!", responseEntity.getBody().get("message"));
    }

    @Test
    void testHandleResourceAlreadyExistsException() {
        ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException("Resource already exists");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleResourceAlreadyExistsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Resource already exists", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleResourceNotValidException() {
        ResourceNotValidException ex = new ResourceNotValidException("Resource not valid");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleResourceNotValidException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Resource not valid", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Resource not found", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("id");

        Map<String, String> response = globalExceptionHandler.handleMethodArgumentTypeMismatchException(ex);

        assertEquals("Invalid Data Provided", response.get("id"));
    }

    @Test
    void testHandleValidationException() {
        FieldError fieldError = new FieldError("objectName", "fieldName", "Invalid value");
        when(bindingResult.getAllErrors()).thenReturn(java.util.Arrays.asList(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        Map<String, String> response = globalExceptionHandler.handleValidationException(ex);

        assertEquals("Invalid value", response.get("fieldName"));
    }

    @Test
    void testHandleBindException() {
        FieldError fieldError = new FieldError("objectName", "fieldName", "Invalid value");
        when(bindingResult.getAllErrors()).thenReturn(java.util.Arrays.asList(fieldError));

        BindException ex = new BindException(bindingResult);
        Map<String, String> response = globalExceptionHandler.handleBindException(ex);

        assertEquals("Invalid value", response.get("fieldName"));
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime exception occurred");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Runtime exception occurred", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("General exception occurred");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("General exception occurred", responseEntity.getBody().getMessage());
    }
}