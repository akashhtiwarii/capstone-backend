package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.RequestSuccessOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestSuccessOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        RequestSuccessOutDTO dto = new RequestSuccessOutDTO();

        assertNull(dto.getMessage());
        String message = "Operation successful!";
        dto.setMessage(message);
        assertEquals(message, dto.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        String message = "Operation successful!";

        RequestSuccessOutDTO dto1 = new RequestSuccessOutDTO(message);
        RequestSuccessOutDTO dto2 = new RequestSuccessOutDTO(message);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setMessage("Operation failed!");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        RequestSuccessOutDTO dto = new RequestSuccessOutDTO();

        String message = "Operation successful!";
        dto.setMessage(message);

        assertEquals("RequestSuccessOutDTO(message=Operation successful!)", dto.toString());
    }
}
