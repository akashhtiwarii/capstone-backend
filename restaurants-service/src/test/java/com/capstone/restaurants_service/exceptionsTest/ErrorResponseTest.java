package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.ErrorResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testGettersAndSetters() {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(404);
        errorResponse.setMessage("Not Found");

        assertEquals(404, errorResponse.getStatus());
        assertEquals("Not Found", errorResponse.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        ErrorResponse errorResponse1 = new ErrorResponse(404, "Not Found");
        ErrorResponse errorResponse2 = new ErrorResponse(404, "Not Found");
        ErrorResponse errorResponse3 = new ErrorResponse(500, "Internal Server Error");

        assertEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1, errorResponse3);

        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
        assertNotEquals(errorResponse1.hashCode(), errorResponse3.hashCode());
    }
}
