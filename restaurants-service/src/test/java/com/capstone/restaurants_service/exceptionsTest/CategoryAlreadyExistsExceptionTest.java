package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.CategoryAlreadyExistException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryAlreadyExistsExceptionTest {

    @Test
    void testCategoryAlreadyExistExceptionMessage() {
        String errorMessage = "Category already exists in the database.";
        CategoryAlreadyExistException exception = new CategoryAlreadyExistException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCategoryAlreadyExistExceptionIsRuntimeException() {
        CategoryAlreadyExistException exception = new CategoryAlreadyExistException("Category already exists.");

        assertTrue(exception instanceof RuntimeException);
    }
}
