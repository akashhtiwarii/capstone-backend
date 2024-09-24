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
 * Global exception handler for handling various exceptions throughout the application.
 * <p>
 * This class provides centralized exception handling for the application, ensuring
 * that appropriate error responses are returned when exceptions are thrown.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Constructs an {@link ErrorResponse} object with the given exception and HTTP status.
     * <p>
     * This method is used to standardize the format of error responses generated
     * from various exceptions.
     * </p>
     * @param ex the exception to include in the error response
     * @param status the HTTP status to set in the error response
     * @return an {@link ErrorResponse} object containing the error details
     */
    private ErrorResponse buildSimpleErrorResponse(final Exception ex, final HttpStatus status) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }

    /**
     * Handles {@link ConstraintViolationException} exceptions.
     * <p>
     * This exception is thrown when a constraint on an entity is violated, such as
     * invalid data being provided.
     * </p>
     * @param ex the {@link ConstraintViolationException} instance
     * @return a {@link ResponseEntity} containing a map with error details and HTTP status
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
     * <p>
     * This exception is thrown when the request body cannot be read or parsed.
     * </p>
     * @param ex the {@link HttpMessageNotReadableException} instance
     * @return a {@link ResponseEntity} containing a map with error details and HTTP status
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
     * Handles {@link ResourceAlreadyExistsException} exceptions.
     * <p>
     * This exception is thrown when an attempt is made to create a resource that already exists.
     * </p>
     * @param ex the {@link ResourceAlreadyExistsException} instance
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} object and HTTP status
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotValidException} exceptions.
     * <p>
     * This exception is thrown when a resource is not valid for processing.
     * </p>
     * @param ex the {@link ResourceNotValidException} instance
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} object and HTTP status
     */
    @ExceptionHandler(ResourceNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleResourceNotValidException(final ResourceNotValidException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles {@link ResourceNotFoundException} exceptions.
     * <p>
     * This exception is thrown when a requested resource cannot be found.
     * </p>
     * @param ex the {@link ResourceNotFoundException} instance
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} object and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} exceptions.
     * <p>
     * This exception is thrown when there is a type mismatch in method arguments.
     * </p>
     * @param ex the {@link MethodArgumentTypeMismatchException} instance
     * @return a map containing the field name and the error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getName();
        String errorMessage = "Invalid Data Provided";
        errors.put(fieldName, errorMessage);
        return errors;
    }

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions.
     * <p>
     * This exception is thrown when validation on method arguments fails.
     * </p>
     * @param ex the {@link MethodArgumentNotValidException} instance
     * @return a map containing the field names and associated error messages
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
     * Handles {@link BindException} exceptions.
     * <p>
     * This exception is thrown when binding errors occur during request handling.
     * </p>
     * @param ex the {@link BindException} instance
     * @return a map containing simplified field names and error messages
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBindException(final BindException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        bindingResult.getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                String simplifiedFieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);

                errors.put(simplifiedFieldName, errorMessage);
            }
        });

        return errors;
    }

    /**
     * Handles {@link RuntimeException} exceptions.
     * <p>
     * This exception is thrown for unexpected runtime errors.
     * </p>
     * @param ex the {@link RuntimeException} instance
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} object and HTTP status
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(final RuntimeException ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles general {@link Exception} exceptions.
     * <p>
     * This method is a catch-all for any exceptions not explicitly handled by other methods.
     * </p>
     * @param ex the {@link Exception} instance
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} object and HTTP status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralException(final Exception ex) {
        ErrorResponse errorResponse = buildSimpleErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
