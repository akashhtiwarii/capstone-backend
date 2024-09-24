package com.capstone.orders_service.exceptionTest;

import com.capstone.orders_service.exceptions.RestaurantConflictException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantConflictExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "Restaurant conflict occurred";
        RestaurantConflictException exception = new RestaurantConflictException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExceptionInstance() {
        RestaurantConflictException exception = new RestaurantConflictException("Error");
        assertTrue(exception instanceof RuntimeException);
    }
}