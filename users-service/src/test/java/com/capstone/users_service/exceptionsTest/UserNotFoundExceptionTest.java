package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundException_Message() {
        String errorMessage = "User not found";
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUserNotFoundException_Inheritance() {
        UserNotFoundException exception = new UserNotFoundException("Test message");

        // Ensure that UserNotFoundException is a subclass of RuntimeException
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
