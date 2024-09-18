package com.capstone.orders_service.exceptions;

import feign.FeignException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for managing different types of exceptions thrown in the application.
 * <p>
 * This class provides centralized exception handling and custom error responses for various exceptions
 * that may occur during request processing.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds a simple error response with the given exception and HTTP status.
     * @param ex The exception that occurred.
     * @param status The HTTP status code to be returned.
     * @return An {@link ErrorResponse} object containing the error details.
     */
    private ErrorResponse buildSimpleErrorResponse(final Exception ex, final HttpStatus status) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }

    /**
     * Handles exceptions thrown when there is a type mismatch in method arguments.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a bad request status.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();

        if (requiredType != null && requiredType.isEnum()) {
            String message = "Invalid status value. Allowed values are: PENDING, ONGOING, COMPLETED";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Data Provided");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



    /**
     * Handles {@link InsufficientAmountException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a bad request status.
     */
    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInsufficientAmountException(final InsufficientAmountException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ConstraintViolationException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing a map with error details and a bad request status.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            final ConstraintViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", "Invalid Request. Try Again!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link HttpMessageNotReadableException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing a map with error details and a bad request status.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "400");
        response.put("message", "Invalid Request. Try Again!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotValidException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a bad request status.
     */
    @ExceptionHandler(ResourceNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleResourceNotValidException(final ResourceNotValidException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotFoundException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a not found status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link FeignException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an error message and appropriate HTTP status.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignStatusException(final FeignException ex) {
        String errorMessage = "Error occurred while communicating with another service: " + ex.getMessage();
        HttpStatus status = HttpStatus.resolve(ex.status());
        return new ResponseEntity<>(errorMessage, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link RestaurantConflictException} exceptions.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with a bad request status.
     */
    @ExceptionHandler(RestaurantConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleRestaurantConflictException(final RestaurantConflictException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors when request parameters or bodies are invalid.
     * @param ex The exception that occurred.
     * @return A map containing field-specific error messages with a bad request status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handles general runtime exceptions that are not explicitly handled elsewhere.
     * @param ex The runtime exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with an internal server error status.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other exceptions not explicitly handled by other methods.
     * @param ex The exception that occurred.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with an internal server error status.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(final Exception ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
