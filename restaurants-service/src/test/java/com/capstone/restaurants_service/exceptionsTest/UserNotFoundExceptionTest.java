package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundExceptionMessage() {
        String errorMessage = "User not found in the database.";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUserNotFoundExceptionIsRuntimeException() {
        UserNotFoundException exception = new UserNotFoundException("User not found.");

        assertTrue(exception instanceof RuntimeException);
    }
}