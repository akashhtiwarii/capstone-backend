package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleInvalidCategoryException() {
        InvalidCategoryException ex = new InvalidCategoryException("Category not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidCategoryException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Category not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleFoodItemNotFoundException() {
        FoodItemNotFoundException ex = new FoodItemNotFoundException("Food item not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleFoodItemNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Food item not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleUserNotValidException() {
        UserNotValidException ex = new UserNotValidException("User not valid");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUserNotValidException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(401, response.getBody().getStatus());
        assertEquals("User not valid", response.getBody().getMessage());
    }

    @Test
    public void testHandleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("User not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUserNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleFoodAlreadyExistsException() {
        FoodAlreadyExistsException ex = new FoodAlreadyExistsException("Food already exists");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleFoodAlreadyExistsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Food already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleCategoryNotFoundException() {
        CategoryNotFoundException ex = new CategoryNotFoundException("Category not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCategoryNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Category not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleCategoryAlreadyExistException() {
        CategoryAlreadyExistException ex = new CategoryAlreadyExistException("Category already exists");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCategoryAlreadyExistException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Category already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleRestaurantsNotFoundException() {
        RestaurantsNotFoundException ex = new RestaurantsNotFoundException("Restaurants not found");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRestaurantsNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Restaurants not found", response.getBody().getMessage());
    }

    @Test
    public void testHandleEmailAlreadyExistsException() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Email already exists");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleEmailAlreadyExistsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Email already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime exception occurred");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Runtime exception occurred", response.getBody().getMessage());
    }

    @Test
    public void testHandleGeneralException() {
        Exception ex = new Exception("General exception occurred");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("General exception occurred", response.getBody().getMessage());
    }

    @Test
    void testHandleValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("field", "defaultMessage");

        Map<String, String> actualErrors = globalExceptionHandler.handleValidationException(ex);

        assertEquals(expectedErrors, actualErrors);
    }
}
