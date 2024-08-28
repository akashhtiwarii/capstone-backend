package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundExceptionMessage() {
        String errorMessage = "User not found";
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testUserNotFoundExceptionInheritance() {
        UserNotFoundException exception = new UserNotFoundException("Test message");
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
