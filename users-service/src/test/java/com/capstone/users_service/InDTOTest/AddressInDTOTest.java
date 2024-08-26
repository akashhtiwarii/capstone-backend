package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.InDTO.AddressInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validAddressInDTO() {
        AddressInDTO addressInDTO = new AddressInDTO(
                "test@example.com",
                "123 Main St",
                123456,
                "New York",
                "NY"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidFieldAddressInDTO() {
        AddressInDTO addressInDTO = new AddressInDTO(
                "invalid-email",
                "123 Main St",
                123456,
                "New York",
                "NY"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertEquals(1, violations.size());
        assertEquals("No valid email found", violations.iterator().next().getMessage());
    }

    @Test
    void blackFieldAddressInDTO() {
        AddressInDTO addressInDTO = new AddressInDTO(
                "test@example.com",
                "",
                123456,
                "New York",
                "NY"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertEquals(1, violations.size());
        assertEquals("Address cannot be empty", violations.iterator().next().getMessage());
    }
}
