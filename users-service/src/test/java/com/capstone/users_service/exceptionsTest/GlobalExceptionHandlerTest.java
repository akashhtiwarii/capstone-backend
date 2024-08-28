package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.AddressNotFoundException;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.exceptions.GlobalExceptionHandler;
import com.capstone.users_service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.HashMap;
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
    void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testHandleAddressNotFoundException() {
        AddressNotFoundException exception = new AddressNotFoundException("Address not found");

        ResponseEntity<String> response = globalExceptionHandler.handleAddressNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Address not found", response.getBody());
    }

    @Test
    void testHandleEmailAlreadyExistsException() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Email already exists");

        ResponseEntity<String> response = globalExceptionHandler.handleEmailAlreadyExistsException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException exception = new RuntimeException("Internal error occurred");

        ResponseEntity<String> response = globalExceptionHandler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal error occurred", response.getBody());
    }

    @Test
    void testHandleGeneralException() {
        Exception exception = new Exception("General error occurred");

        ResponseEntity<String> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("General error occurred", response.getBody());
    }

    @Test
    void testHandleValidationException() {
        FieldError fieldError = new FieldError("objectName", "fieldName", "Validation failed");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("fieldName", "Validation failed");

        Map<String, String> errors = globalExceptionHandler.handleValidationException(exception);

        assertEquals(expectedErrors, errors);
    }
}

