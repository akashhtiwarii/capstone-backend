package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceAlreadyExistsExceptionTest {

    @Test
    public void testResourceAlreadyExistsExceptionConstructor() {
        String errorMessage = "Resource already exists";
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}