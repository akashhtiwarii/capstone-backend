package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.dto.AddressInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
                "test@gmail.com",
                "Saket Colony, Durg",
                123456,
                "Bhilai",
                "CG"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidFieldAddressInDTO() {
        AddressInDTO addressInDTO = new AddressInDTO(
                "invalid-email",
                "Saket Colony, Durg",
                123456,
                "Bhilai",
                "CG"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertEquals(1, violations.size());
        assertEquals("No valid email found", violations.iterator().next().getMessage());
    }

    @Test
    void blackFieldAddressInDTO() {
        AddressInDTO addressInDTO = new AddressInDTO(
                "test@gmail.com",
                "",
                123456,
                "Bhilai",
                "CG"
        );

        Set<ConstraintViolation<AddressInDTO>> violations = validator.validate(addressInDTO);

        assertEquals(1, violations.size());
        assertEquals("Address cannot be empty", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("john.doe@gmail.com");
        addressInDTO.setAddress("Saket Colony, Durg");
        addressInDTO.setPincode(123456);
        addressInDTO.setCity("Durg");
        addressInDTO.setState("CG");

        assertEquals("john.doe@gmail.com", addressInDTO.getEmail());
        assertEquals("Saket Colony, Durg", addressInDTO.getAddress());
        assertEquals(123456, addressInDTO.getPincode());
        assertEquals("Durg", addressInDTO.getCity());
        assertEquals("CG", addressInDTO.getState());
    }

    @Test
    void testEqualsAndHashCode() {

        AddressInDTO address1 = new AddressInDTO(
                "john.doe@gmail.com",
                "Saket Colony, Durg",
                123456,
                "Durg",
                "CG"
        );
        AddressInDTO address2 = new AddressInDTO(
                "john.doe@gmail.com",
                "Saket Colony, Durg",
                123456,
                "Durg",
                "CG"
        );
        AddressInDTO address3 = new AddressInDTO(
                "jane.doe@gmail.com",
                "Supela, Bhilai",
                654321,
                "Durg",
                "CG"
        );

        assertEquals(address1, address2);
        assertNotEquals(address1, address3);
        assertNotEquals(address2, address3);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }
}
