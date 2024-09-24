package com.capstone.orders_service.exceptionTest;

import com.capstone.orders_service.exceptions.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseTest {

    @Test
    public void testGetterAndSetter() {
        ErrorResponse errorResponse = new ErrorResponse();

        assertEquals(0, errorResponse.getStatus());
        errorResponse.setStatus(404);
        assertEquals(404, errorResponse.getStatus());

        assertNull(errorResponse.getMessage());
        String message = "Resource not found";
        errorResponse.setMessage(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    public void testToString() {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error");
        String expectedString = "ErrorResponse(status=500, message=Internal Server Error)";
        assertEquals(expectedString, errorResponse.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        ErrorResponse errorResponse1 = new ErrorResponse(404, "Resource not found");

        assertEquals(errorResponse1, errorResponse1);
        assertEquals(errorResponse1.hashCode(), errorResponse1.hashCode());

        assertNotEquals(errorResponse1, new Object());

        ErrorResponse errorResponse2 = new ErrorResponse(404, "Resource not found");
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse2 = new ErrorResponse(500, "Resource not found");
        assertNotEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse2 = new ErrorResponse(404, "Not found");
        assertNotEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse1 = new ErrorResponse();
        errorResponse2 = new ErrorResponse();
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }
}
