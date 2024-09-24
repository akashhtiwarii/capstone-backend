package com.capstone.orders_service.exceptionTest;


import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.exceptions.*;
import feign.FeignException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleConstraintViolationException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().get("status"));
        assertEquals("Invalid Request. Try Again!", response.getBody().get("message"));
    }

    @Test
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleHttpMessageNotReadableException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().get("status"));
        assertEquals("Invalid Request. Try Again!", response.getBody().get("message"));
    }

    @Test
    void testHandleTypeMismatchExceptionForEnum() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        Mockito.when(ex.getRequiredType()).thenReturn((Class) Status.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTypeMismatchException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid status value. Allowed values are: PENDING, ONGOING, COMPLETED",
                response.getBody().getMessage());
    }

    @Test
    void testHandleTypeMismatchExceptionForOtherTypes() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        Mockito.when(ex.getRequiredType()).thenReturn((Class) Integer.class);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTypeMismatchException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Invalid Data Provided", response.getBody().getMessage());
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void testHandleFeignException() {
        FeignException ex = mock(FeignException.class);
        Mockito.when(ex.status()).thenReturn(404);
        Mockito.when(ex.getMessage()).thenReturn("Service Unavailable");

        ResponseEntity<String> response = globalExceptionHandler.handleFeignStatusException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error occurred while communicating with another service: Service Unavailable",
                response.getBody());
    }

    @Test
    void testHandleResourceNotValidException() {
        ResourceNotValidException ex = new ResourceNotValidException("Invalid resource");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotValidException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid resource", response.getBody().getMessage());
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("General error");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("General error", response.getBody().getMessage());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime error");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Runtime error", response.getBody().getMessage());
    }

    @Test
    void testHandleRestaurantConflictException() {
        RestaurantConflictException ex = new RestaurantConflictException("Restaurant conflict error");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRestaurantConflictException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Restaurant conflict error", response.getBody().getMessage());
    }

    @Test
    void testHandleInsufficientAmountException() {
        InsufficientAmountException ex = new InsufficientAmountException("Insufficient amount");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInsufficientAmountException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient amount", response.getBody().getMessage());
    }

}