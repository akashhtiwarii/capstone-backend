package com.capstone.orders_service.exceptions;

import feign.FeignException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleFoodItemNotFoundException(FoodItemNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
     * Handle Resource Not Found Exception.
     * @param ex
     * @return ErrorResponse Object
     */
    @ExceptionHandler(ResourceNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleResourceNotValidException(ResourceNotValidException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Resource Not Found Exception.
     * @param ex
     * @return ErrorResponse Object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
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

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignStatusException(FeignException ex) {
        // Custom error response
        String errorMessage = "Error occurred while communicating with another service: " + ex.getMessage();
        HttpStatus status = HttpStatus.resolve(ex.status());
        return new ResponseEntity<>(errorMessage, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
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
     * Restaurant Conflict Exception.
     * @param ex
     * @return Error Response Object
     */
    @ExceptionHandler(CartItemDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCartItemDoesNotExistsException(CartItemDoesNotExistsException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Restaurant Conflict Exception.
     * @param ex
     * @return Error Response Object
     */
    @ExceptionHandler(RestaurantConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleRestaurantConflictException(RestaurantConflictException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
