package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.LoginRequestInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestInDTOTest {

    @Test
    public void testGetterAndSetter() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();

        assertNull(loginRequestInDTO.getEmail());
        String email = "email@example.com";
        loginRequestInDTO.setEmail(email);
        assertEquals(email, loginRequestInDTO.getEmail());

        assertNull(loginRequestInDTO.getPassword());
        String password = "password123";
        loginRequestInDTO.setPassword(password);
        assertEquals(password, loginRequestInDTO.getPassword());
    }

    @Test
    public void testEqualsAndHashcode() {
        String email = "email@example.com";
        String password = "password123";

        LoginRequestInDTO loginRequestInDTO1 = buildLoginRequestInDTO(email, password);

        assertEquals(loginRequestInDTO1, loginRequestInDTO1);
        assertEquals(loginRequestInDTO1.hashCode(), loginRequestInDTO1.hashCode());

        assertNotEquals(loginRequestInDTO1, new Object());

        LoginRequestInDTO loginRequestInDTO2 = buildLoginRequestInDTO(email, password);
        assertEquals(loginRequestInDTO1, loginRequestInDTO2);
        assertEquals(loginRequestInDTO1.hashCode(), loginRequestInDTO2.hashCode());

        loginRequestInDTO2 = buildLoginRequestInDTO(email + ".com", password);
        assertNotEquals(loginRequestInDTO1, loginRequestInDTO2);
        assertNotEquals(loginRequestInDTO1.hashCode(), loginRequestInDTO2.hashCode());

        loginRequestInDTO2 = buildLoginRequestInDTO(email, password + "456");
        assertNotEquals(loginRequestInDTO1, loginRequestInDTO2);
        assertNotEquals(loginRequestInDTO1.hashCode(), loginRequestInDTO2.hashCode());
    }

    @Test
    public void testToString() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();

        String email = "email@example.com";
        String password = "password123";

        loginRequestInDTO.setEmail(email);
        loginRequestInDTO.setPassword(password);

        assertEquals("LoginRequestInDTO(email=email@example.com, password=password123)", loginRequestInDTO.toString());
    }

    private LoginRequestInDTO buildLoginRequestInDTO(String email, String password) {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail(email);
        loginRequestInDTO.setPassword(password);
        return loginRequestInDTO;
    }
}
