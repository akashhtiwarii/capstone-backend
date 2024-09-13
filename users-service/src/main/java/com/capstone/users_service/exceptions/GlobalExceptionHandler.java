package com.capstone.users_service.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that provides centralized exception handling
 * for the application. It handles various types of exceptions and
 * returns appropriate error responses to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Creates an {@link ErrorResponse} object with the given exception and HTTP status.
     *
     * @param ex the exception that occurred
     * @param status the HTTP status associated with the error
     * @return an {@link ErrorResponse} object containing the status and error message
     */
    private ErrorResponse buildSimpleErrorResponse(Exception ex, HttpStatus status) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }

    /**
     * Handles {@link ConstraintViolationException} thrown when a constraint is violated.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing a map with error status and message
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
     * Handles {@link HttpMessageNotReadableException} thrown when the request body is not readable.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing a map with error status and message
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
     * Handles {@link ResourceAlreadyExistsException} thrown when a resource already exists.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotValidException} thrown when a resource is not valid.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(ResourceNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleResourceNotValidException(ResourceNotValidException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles {@link ResourceNotFoundException} thrown when a requested resource is not found.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link RuntimeException} thrown when an unexpected error occurs.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles general {@link Exception} cases not specifically handled by other methods.
     *
     * @param ex the exception that occurred
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} thrown when a method argument fails validation.
     *
     * @param ex the exception that occurred
     * @return a map of field names and their respective error messages
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
}
