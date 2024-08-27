package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.UserInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserInDTOTest {

    private Validator validator;
    private UserInDTO userInDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        userInDTO = new UserInDTO();
        userInDTO.setName("Akash Tiwari");
        userInDTO.setPassword("Password1!");
        userInDTO.setEmail("akash@gmail.com");
        userInDTO.setPhone("1234567890");
        userInDTO.setRole(Role.USER);
    }

    @Test
    void testValidUserInDTO() {
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName() {
        userInDTO.setName("J@hn!");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("A valid name is mandatory", violation.getMessage());
    }

    @Test
    void testBlankName() {
        userInDTO.setName("");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("A valid name is mandatory", violation.getMessage());  // Adjusted message to match @NotBlank
    }

    @Test
    void testInvalidPassword() {
        userInDTO.setPassword("pass");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Password should be minimum 8 characters", violation.getMessage());
    }

    @Test
    void testBlankPassword() {
        userInDTO.setPassword("");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Password should be minimum 8 characters", violation.getMessage());
    }

    @Test
    void testInvalidEmail() {
        userInDTO.setEmail("invalid-email");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Valid Email not found", violation.getMessage());
    }

    @Test
    void testBlankEmail() {
        userInDTO.setEmail("");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Email is mandatory", violation.getMessage());
    }

    @Test
    void testInvalidPhoneNumber() {
        userInDTO.setPhone("123");
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Phone number should be valid", violation.getMessage());
    }

    @Test
    void testNullRole() {
        userInDTO.setRole(null);
        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);
        assertFalse(violations.isEmpty());

        ConstraintViolation<UserInDTO> violation = violations.iterator().next();
        assertEquals("Role is mandatory", violation.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        UserInDTO userInDTO2 = new UserInDTO("Akash Tiwari", "Password1!", "akash@gmail.com", "1234567890", Role.USER);

        assertEquals(userInDTO, userInDTO2);
        assertEquals(userInDTO.hashCode(), userInDTO2.hashCode());

        userInDTO2.setEmail("different@example.com");
        assertNotEquals(userInDTO, userInDTO2);
        assertNotEquals(userInDTO.hashCode(), userInDTO2.hashCode());
    }
}