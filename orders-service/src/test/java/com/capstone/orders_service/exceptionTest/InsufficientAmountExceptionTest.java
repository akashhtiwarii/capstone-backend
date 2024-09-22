package com.capstone.orders_service.exceptionTest;

import com.capstone.orders_service.exceptions.InsufficientAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsufficientAmountExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Insufficient balance in wallet";
        InsufficientAmountException exception = new InsufficientAmountException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionInstance() {
        InsufficientAmountException exception = new InsufficientAmountException("Error");
        assertTrue(exception instanceof RuntimeException);
    }
}
