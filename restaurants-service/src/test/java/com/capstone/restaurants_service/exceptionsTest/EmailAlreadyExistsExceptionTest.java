package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailAlreadyExistsExceptionTest {

    @Test
    void testEmailAlreadyExistsExceptionMessage() {
        String errorMessage = "Email already exists in the database.";
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testEmailAlreadyExistsExceptionIsRuntimeException() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Email already exists.");

        assertTrue(exception instanceof RuntimeException);
    }
}

