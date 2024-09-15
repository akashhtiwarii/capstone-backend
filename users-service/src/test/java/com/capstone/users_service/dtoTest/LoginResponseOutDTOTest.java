package com.capstone.users_service.dtoTest;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginResponseOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();

        assertEquals(0, loginResponseOutDTO.getUserId());
        long userId = 12345L;
        loginResponseOutDTO.setUserId(userId);
        assertEquals(userId, loginResponseOutDTO.getUserId());

        assertNull(loginResponseOutDTO.getEmail());
        String email = "test@example.com";
        loginResponseOutDTO.setEmail(email);
        assertEquals(email, loginResponseOutDTO.getEmail());

        assertNull(loginResponseOutDTO.getName());
        String name = "John Doe";
        loginResponseOutDTO.setName(name);
        assertEquals(name, loginResponseOutDTO.getName());

        assertNull(loginResponseOutDTO.getPhone());
        String phone = "1234567890";
        loginResponseOutDTO.setPhone(phone);
        assertEquals(phone, loginResponseOutDTO.getPhone());

        assertNull(loginResponseOutDTO.getRole());
        Role role = Role.USER;
        loginResponseOutDTO.setRole(role);
        assertEquals(role, loginResponseOutDTO.getRole());

        assertNull(loginResponseOutDTO.getMessage());
        String message = "Login successful";
        loginResponseOutDTO.setMessage(message);
        assertEquals(message, loginResponseOutDTO.getMessage());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 12345L;
        String email = "test@example.com";
        String name = "John Doe";
        String phone = "1234567890";
        Role role = Role.USER;
        String message = "Login successful";

        LoginResponseOutDTO dto1 = buildLoginResponseOutDTO(userId, email, name, phone, role, message);
        LoginResponseOutDTO dto2 = buildLoginResponseOutDTO(userId, email, name, phone, role, message);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setUserId(54321L);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();

        long userId = 12345L;
        loginResponseOutDTO.setUserId(userId);

        String email = "test@example.com";
        loginResponseOutDTO.setEmail(email);

        String name = "John Doe";
        loginResponseOutDTO.setName(name);

        String phone = "1234567890";
        loginResponseOutDTO.setPhone(phone);

        Role role = Role.USER;
        loginResponseOutDTO.setRole(role);

        String message = "Login successful";
        loginResponseOutDTO.setMessage(message);

        assertEquals("LoginResponseOutDTO(userId=12345, email=test@example.com, name=John Doe, phone=1234567890, role=USER, message=Login successful)", loginResponseOutDTO.toString());
    }

    private LoginResponseOutDTO buildLoginResponseOutDTO(long userId, String email, String name, String phone, Role role, String message) {
        return new LoginResponseOutDTO(userId, email, name, phone, role, message);
    }
}
