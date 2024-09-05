package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.InvalidCategoryException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidCategoryExceptionTest {

    @Test
    void testInvalidCategoryExceptionMessage() {
        String errorMessage = "Category is invalid.";
        InvalidCategoryException exception = new InvalidCategoryException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testInvalidCategoryExceptionIsRuntimeException() {
        InvalidCategoryException exception = new InvalidCategoryException("Invalid category.");

        assertTrue(exception instanceof RuntimeException);
    }
}
