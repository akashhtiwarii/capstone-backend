package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantsNotFoundExceptionTest {

    @Test
    void testRestaurantsNotFoundExceptionMessage() {
        String errorMessage = "No restaurants found in the database.";
        RestaurantsNotFoundException exception = new RestaurantsNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testRestaurantsNotFoundExceptionIsRuntimeException() {
        RestaurantsNotFoundException exception = new RestaurantsNotFoundException("Restaurants not found.");

        assertTrue(exception instanceof RuntimeException);
    }
}
