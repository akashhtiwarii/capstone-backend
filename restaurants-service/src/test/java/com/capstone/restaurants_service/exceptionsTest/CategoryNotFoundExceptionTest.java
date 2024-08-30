package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryNotFoundExceptionTest {

    @Test
    void testCategoryNotFoundExceptionMessage() {
        String errorMessage = "Category not found in the database.";
        CategoryNotFoundException exception = new CategoryNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCategoryNotFoundExceptionIsRuntimeException() {
        CategoryNotFoundException exception = new CategoryNotFoundException("Category not found.");

        assertTrue(exception instanceof RuntimeException);
    }
}
