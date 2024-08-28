package com.capstone.users_service.exceptionsTest;


import com.capstone.users_service.exceptions.RestaurantsNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantNotFoundExceptionTest {

    @Test
    public void testRestaurantsNotFoundExceptionMessage() {
        String errorMessage = "No Restaurants found in the database";

        RestaurantsNotFoundException exception = new RestaurantsNotFoundException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}