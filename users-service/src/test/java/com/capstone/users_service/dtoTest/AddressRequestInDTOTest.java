package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.AddressRequestInDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressRequestInDTOTest {

    @Test
    void testGettersAndSetters() {
        AddressRequestInDTO dto = new AddressRequestInDTO();
        dto.setEmail("test@example.com");
        assertEquals("test@example.com", dto.getEmail());
    }

    @Test
    void testEquals() {
        AddressRequestInDTO dto1 = new AddressRequestInDTO("test@example.com");
        AddressRequestInDTO dto2 = new AddressRequestInDTO("test@example.com");
        AddressRequestInDTO dto3 = new AddressRequestInDTO("another@example.com");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testHashCode() {
        AddressRequestInDTO dto1 = new AddressRequestInDTO("test@example.com");
        AddressRequestInDTO dto2 = new AddressRequestInDTO("test@example.com");
        AddressRequestInDTO dto3 = new AddressRequestInDTO("another@example.com");

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        AddressRequestInDTO dto = new AddressRequestInDTO("test@example.com");
        String expected = "AddressRequestInDTO(email=test@example.com)";
        assertEquals(expected, dto.toString());
    }
}

