package com.capstone.orders_service.exceptionTest;

import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionInstance() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Error");
        assertTrue(exception instanceof RuntimeException);
    }
}
