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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validUserInDTO() {
        UserInDTO userInDTO = new UserInDTO(
                "John Doe",
                "Password@123",
                "john.doe@example.com",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNameIsBlank_thenOneViolation() {
        UserInDTO userInDTO = new UserInDTO(
                "",
                "Password@123",
                "john.doe@example.com",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);

        assertEquals(1, violations.size());
        assertEquals("Name is mandatory", violations.iterator().next().getMessage());
    }

    @Test
    void invalidPasswordUserInDTO() {
        UserInDTO userInDTO = new UserInDTO(
                "John Doe",
                "short",
                "john.doe@example.com",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);

        assertEquals(2, violations.size());
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character", violations.iterator().next().getMessage());
    }

    @Test
    void invalidEmailUserInDTO() {
        UserInDTO userInDTO = new UserInDTO(
                "John Doe",
                "Password@123",
                "invalid-email",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);

        assertEquals(1, violations.size());
        assertEquals("Valid Email not found", violations.iterator().next().getMessage());
    }

    @Test
    void invalidPhoneUserInDTO() {
        UserInDTO userInDTO = new UserInDTO(
                "John Doe",
                "Password@123",
                "john.doe@example.com",
                "12345",  // Invalid phone number
                "123 Elm Street",
                Role.USER
        );

        Set<ConstraintViolation<UserInDTO>> violations = validator.validate(userInDTO);

        assertEquals(1, violations.size());
        assertEquals("Phone number should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        UserInDTO user1 = new UserInDTO(
                "John Doe",
                "Password@123",
                "john.doe@example.com",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        UserInDTO user2 = new UserInDTO(
                "John Doe",
                "Password@123",
                "john.doe@example.com",
                "1234567890",
                "123 Elm Street",
                Role.USER
        );

        UserInDTO user3 = new UserInDTO(
                "Jane Doe",
                "Password@123",
                "jane.doe@example.com",
                "0987654321",
                "456 Oak Street",
                Role.OWNER
        );

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertTrue(!user1.equals(user3));
        assertTrue(user1.hashCode() != user3.hashCode());
    }
}
