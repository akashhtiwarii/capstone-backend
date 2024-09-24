package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.dto.RequestSuccessOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestSuccessOutDTOTest {

    @Test
    void testGettersSetters() {
        RequestSuccessOutDTO dto = new RequestSuccessOutDTO();
        String successMessage = "Operation successful";
        dto.setMessage(successMessage);

        assertEquals(successMessage, dto.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        RequestSuccessOutDTO dto1 = new RequestSuccessOutDTO("Operation successful");
        RequestSuccessOutDTO dto2 = new RequestSuccessOutDTO("Operation successful");
        RequestSuccessOutDTO dto3 = new RequestSuccessOutDTO("Operation failed");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}

