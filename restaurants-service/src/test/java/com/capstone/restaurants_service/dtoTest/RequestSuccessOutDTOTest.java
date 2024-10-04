package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.RequestSuccessOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestSuccessOutDTOTest {

    @Test
    public void testGettersAndSetters() {
        RequestSuccessOutDTO dto = new RequestSuccessOutDTO();
        dto.setMessage("Success");

        assertEquals("Success", dto.getMessage());
    }

    @Test
    public void testEquals() {
        RequestSuccessOutDTO dto1 = new RequestSuccessOutDTO("Operation completed successfully.");
        RequestSuccessOutDTO dto2 = new RequestSuccessOutDTO("Operation completed successfully.");
        RequestSuccessOutDTO dto3 = new RequestSuccessOutDTO("Different message");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto2, dto3);
    }

    @Test
    public void testHashCode() {
        RequestSuccessOutDTO dto1 = new RequestSuccessOutDTO("Operation completed successfully.");
        RequestSuccessOutDTO dto2 = new RequestSuccessOutDTO("Operation completed successfully.");
        RequestSuccessOutDTO dto3 = new RequestSuccessOutDTO("Different message");

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    public void testToString() {
        RequestSuccessOutDTO dto = new RequestSuccessOutDTO();
        dto.setMessage("Request completed successfully");

        String expectedString = "RequestSuccessOutDTO(message=Request completed successfully)";

        assertEquals(expectedString, dto.toString());
    }
}
