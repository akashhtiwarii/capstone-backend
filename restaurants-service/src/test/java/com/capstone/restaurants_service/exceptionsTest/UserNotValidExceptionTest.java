package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.UserNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserNotValidExceptionTest {

    @Test
    void testUserNotValidExceptionMessage() {
        String errorMessage = "User is not valid.";
        UserNotValidException exception = new UserNotValidException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUserNotValidExceptionIsRuntimeException() {
        UserNotValidException exception = new UserNotValidException("User not valid.");

        assertTrue(exception instanceof RuntimeException);
    }
}
