package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.InDTO.LoginRequestInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validLoginRequestInDTO() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO(
                "test@example.com",
                "securePassword123"
        );

        Set<ConstraintViolation<LoginRequestInDTO>> violations = validator.validate(loginRequestInDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmailLoginRequestInDTO() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO(
                "invalid-email",
                "securePassword123"
        );

        Set<ConstraintViolation<LoginRequestInDTO>> violations = validator.validate(loginRequestInDTO);

        assertEquals(1, violations.size());
        assertEquals("Valid email required", violations.iterator().next().getMessage());
    }

    @Test
    void blankPasswordLoginRequestInDTO() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO(
                "test@example.com",
                ""
        );
        Set<ConstraintViolation<LoginRequestInDTO>> violations = validator.validate(loginRequestInDTO);
        assertEquals(1, violations.size());
        assertEquals("Password cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("john.doe@example.com");
        loginRequestInDTO.setPassword("password123");
        assertEquals("john.doe@example.com", loginRequestInDTO.getEmail());
        assertEquals("password123", loginRequestInDTO.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginRequestInDTO loginRequest1 = new LoginRequestInDTO("john.doe@example.com", "password123");
        LoginRequestInDTO loginRequest2 = new LoginRequestInDTO("john.doe@example.com", "password123");
        LoginRequestInDTO loginRequest3 = new LoginRequestInDTO("jane.doe@example.com", "password123");
        assertEquals(loginRequest1, loginRequest2);
        assertNotEquals(loginRequest1, loginRequest3);
        assertNotEquals(loginRequest2, loginRequest3);
        assertEquals(loginRequest1.hashCode(), loginRequest2.hashCode());
        assertNotEquals(loginRequest1.hashCode(), loginRequest3.hashCode());
    }

}
