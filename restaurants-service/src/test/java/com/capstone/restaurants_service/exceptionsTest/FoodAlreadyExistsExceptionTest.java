package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.FoodAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodAlreadyExistsExceptionTest {

    @Test
    void testFoodAlreadyExistsExceptionMessage() {
        String errorMessage = "Food item already exists in the database.";
        FoodAlreadyExistsException exception = new FoodAlreadyExistsException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testFoodAlreadyExistsExceptionIsRuntimeException() {
        FoodAlreadyExistsException exception = new FoodAlreadyExistsException("Food item already exists.");

        assertTrue(exception instanceof RuntimeException);
    }
}
