package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.InDTO.AddressRequestInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressRequestInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEmail() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO("test@example.com");

        Set<ConstraintViolation<AddressRequestInDTO>> violations = validator.validate(addressRequestInDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO("invalid-email");

        Set<ConstraintViolation<AddressRequestInDTO>> violations = validator.validate(addressRequestInDTO);

        assertEquals(1, violations.size());
        assertEquals("No valid email found", violations.iterator().next().getMessage());
    }
}
