package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAlreadyExistsExceptionTest {

    @Test
    void testEmailAlreadyExistsException_Message() {
        String errorMessage = "Email already exists in the database";
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            throw new EmailAlreadyExistsException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testEmailAlreadyExistsException_Inheritance() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Test message");

        // Ensure that EmailAlreadyExistsException is a subclass of RuntimeException
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}

