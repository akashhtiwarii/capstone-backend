package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.UserNotValidException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserNotValidExceptionTest {

    @Test
    public void testUserNotValidExceptionMessage() {
        String errorMessage = "User is not valid";

        UserNotValidException exception = new UserNotValidException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}
