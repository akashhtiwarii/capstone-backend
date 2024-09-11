package com.capstone.restaurants_service.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Error Response Creation.
     * @param ex
     * @param status
     * @return ErrorResponse Object
     */
    private ErrorResponse buildSimpleErrorResponse(Exception ex, HttpStatus status) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }

    /**
     * Constraint Violation.
     * @param ex
     * @return Error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", "Invalid Request. Try Again!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Request Body not found.
     * @param ex
     * @return Error message and status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", "Invalid Request. Try Again!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Invalid Category Exception.
     * @param ex
     * @return ErrorResponse Object.
     */
    @ExceptionHandler(InvalidCategoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleInvalidCategoryException(InvalidCategoryException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle FoodItemNotFoundException.
     * @param ex
     * @return Error Response object
     */
    @ExceptionHandler(FoodItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleFoodItemNotFoundException(FoodItemNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    /**
     * Handle User Not Valid Exception.
     * @param ex User not valid exception
     * @return Error with a message
     */
    @ExceptionHandler(UserNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleUserNotValidException(UserNotValidException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle User Not Found Exception.
     * @param ex User not found exception
     * @return Error with a message
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    /**
     * Food already in the menu.
     * @param ex
     * @return ErrorResponse Object
     */
    @ExceptionHandler(FoodAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleFoodAlreadyExistsException(FoodAlreadyExistsException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handle Category Not Found Exception.
     * @param ex
     * @return Error with a message
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle Category Already Exist Exception.
     * @param ex
     * @return Error with a message
     */
    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistException(CategoryAlreadyExistException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handle Restaurants Not Found Exception.
     * @param ex Restaurants not found exception
     * @return Error with a message
     */
    @ExceptionHandler(RestaurantsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleRestaurantsNotFoundException(RestaurantsNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    /**
     * Handle EmailAlreadyExists Exception.
     * @param ex Email already in database exception object
     * @return Error message with an error code.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getName();
        String errorMessage = "Invalid Data Provided";
        errors.put(fieldName, errorMessage);
        return errors;
    }

    /**
     * Handle Validation Errors.
     * @param ex Exception object
     * @return all the errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        bindingResult.getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                // Extracting the field name from the error
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();

                // Simplifying field names by removing any path or nested information
                String simplifiedFieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);

                errors.put(simplifiedFieldName, errorMessage);
            }
        });

        return errors;
    }

    /**
     * Handle Runtime Exception.
     * @param ex Runtime exception object
     * @return Error message with an error code.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle General Exception.
     * @param ex exception object
     * @return Error message with an error code.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

