package com.capstone.users_service.OutDTOTest;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseOutDTOTest {

    @Test
    void testGettersAndSetters() {
        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();
        loginResponseOutDTO.setUserId(1L);
        loginResponseOutDTO.setEmail("john.doe@example.com");
        loginResponseOutDTO.setName("John Doe");
        loginResponseOutDTO.setPhone("1234567890");
        loginResponseOutDTO.setRole(Role.USER);
        loginResponseOutDTO.setMessage("Login Successful");
        assertEquals(1L, loginResponseOutDTO.getUserId());
        assertEquals("john.doe@example.com", loginResponseOutDTO.getEmail());
        assertEquals("John Doe", loginResponseOutDTO.getName());
        assertEquals("1234567890", loginResponseOutDTO.getPhone());
        assertEquals(Role.USER, loginResponseOutDTO.getRole());
        assertEquals("Login Successful", loginResponseOutDTO.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginResponseOutDTO loginResponse1 = new LoginResponseOutDTO(1L, "john.doe@example.com", "John Doe", "1234567890", Role.USER, "Login Successful");
        LoginResponseOutDTO loginResponse2 = new LoginResponseOutDTO(1L, "john.doe@example.com", "John Doe", "1234567890", Role.USER, "Login Successful");
        LoginResponseOutDTO loginResponse3 = new LoginResponseOutDTO(2L, "jane.doe@example.com", "Jane Doe", "0987654321", Role.OWNER, "Login Failed");
        assertEquals(loginResponse1, loginResponse2);
        assertNotEquals(loginResponse1, loginResponse3);
        assertNotEquals(loginResponse2, loginResponse3);
        assertEquals(loginResponse1.hashCode(), loginResponse2.hashCode());
        assertNotEquals(loginResponse1.hashCode(), loginResponse3.hashCode());
    }
}
