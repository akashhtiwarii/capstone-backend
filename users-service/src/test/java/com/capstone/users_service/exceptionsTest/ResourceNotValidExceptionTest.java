package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.ResourceNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceNotValidExceptionTest {

    @Test
    public void testResourceNotValidExceptionConstructor() {
        String errorMessage = "Resource is not valid";
        ResourceNotValidException exception = new ResourceNotValidException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}
