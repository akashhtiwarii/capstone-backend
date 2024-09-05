package com.capstone.restaurants_service.dtoTest.OutDTOTest;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.dto.UserOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserOutDTOTest {

    @Test
    void testGettersAndSetters() {
        UserOutDTO dto = new UserOutDTO();
        dto.setUserId(1L);
        dto.setEmail("user@example.com");
        dto.setName("John Doe");
        dto.setPhone("1234567890");
        dto.setRole(Role.USER);

        assertEquals(1L, dto.getUserId());
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("John Doe", dto.getName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(Role.USER, dto.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        UserOutDTO dto1 = new UserOutDTO(1L, "user@example.com", "John Doe", "1234567890", Role.USER);
        UserOutDTO dto2 = new UserOutDTO(1L, "user@example.com", "John Doe", "1234567890", Role.USER);
        UserOutDTO dto3 = new UserOutDTO(2L, "anotheruser@example.com", "Jane Doe", "0987654321", Role.OWNER);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
