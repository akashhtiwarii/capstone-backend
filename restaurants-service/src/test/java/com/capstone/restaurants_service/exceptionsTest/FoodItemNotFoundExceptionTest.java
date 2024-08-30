package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.FoodItemNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodItemNotFoundExceptionTest {

    @Test
    void testFoodItemNotFoundExceptionMessage() {
        String errorMessage = "Food item not found in the database.";
        FoodItemNotFoundException exception = new FoodItemNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testFoodItemNotFoundExceptionIsRuntimeException() {
        FoodItemNotFoundException exception = new FoodItemNotFoundException("Food item not found.");

        assertTrue(exception instanceof RuntimeException);
    }
}
