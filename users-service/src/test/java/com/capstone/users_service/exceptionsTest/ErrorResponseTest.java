package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.ErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseTest {

    @Test
    public void testGetterAndSetter() {
        ErrorResponse errorResponse = new ErrorResponse();

        assertEquals(0, errorResponse.getStatus());
        int status = 400;
        errorResponse.setStatus(status);
        assertEquals(status, errorResponse.getStatus());

        assertNull(errorResponse.getMessage());
        String message = "Bad Request";
        errorResponse.setMessage(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    public void testToString() {
        ErrorResponse errorResponse = new ErrorResponse();

        int status = 500;
        errorResponse.setStatus(status);

        String message = "Internal Server Error";
        errorResponse.setMessage(message);

        assertEquals("ErrorResponse(status=500, message=Internal Server Error)", errorResponse.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        int status = 404;
        String message = "Not Found";

        ErrorResponse errorResponse1 = buildErrorResponse(status, message);

        assertEquals(errorResponse1, errorResponse1);
        assertEquals(errorResponse1.hashCode(), errorResponse1.hashCode());

        assertNotEquals(errorResponse1, new Object());

        ErrorResponse errorResponse2 = buildErrorResponse(status, message);
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse2 = buildErrorResponse(status + 1, message);
        assertNotEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse2 = buildErrorResponse(status, message + " Updated");
        assertNotEquals(errorResponse1, errorResponse2);
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode());

        errorResponse1 = new ErrorResponse();
        errorResponse2 = new ErrorResponse();
        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }

    private ErrorResponse buildErrorResponse(int status, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}