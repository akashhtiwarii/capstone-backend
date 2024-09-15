package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateRestaurantInDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUpdateRestaurantInDTO() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 1L, "Test Restaurant", "test@gmail.com", "9876543210", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation violations");
    }

    @Test
    public void testInvalidLoggedInOwnerId() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                0L, 1L, "Test Restaurant", "test@gmail.com", "9876543210", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for loggedInOwnerId");
        assertEquals("Valid User ID Required", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidRestaurantId() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 0L, "Test Restaurant", "test@gmail.com", "9876543210", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for restaurantId");
        assertEquals("Valid Restaurant ID Required", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidRestaurantName() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 1L, "", "test@gmail.com", "9876543210", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for name");
        assertEquals("Enter a valid name for restaurant", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidEmail() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 1L, "Test Restaurant", "invalid_email", "9876543210", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for email");
        assertEquals("Enter a valid email ID for restaurant", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPhoneNumber() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 1L, "Test Restaurant", "test@gmail.com", "12345", "123 Test Address"
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for phone");
        assertEquals("Phone number should be valid", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidAddress() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(
                1L, 1L, "Test Restaurant", "test@gmail.com", "9876543210", ""
        );
        Set<ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation violations for address");
        assertEquals("Address for restaurant cannot be empty", violations.iterator().next().getMessage());
    }
}