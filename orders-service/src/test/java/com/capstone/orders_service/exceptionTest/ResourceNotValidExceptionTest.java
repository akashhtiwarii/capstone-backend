package com.capstone.orders_service.exceptionTest;

import com.capstone.orders_service.exceptions.ResourceNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceNotValidExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Resource is not valid";
        ResourceNotValidException exception = new ResourceNotValidException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionInstance() {
        ResourceNotValidException exception = new ResourceNotValidException("Error");
        assertTrue(exception instanceof RuntimeException);
    }
}