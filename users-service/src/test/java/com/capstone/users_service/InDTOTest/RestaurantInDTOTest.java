package com.capstone.users_service.InDTOTest;

import com.capstone.users_service.InDTO.RestaurantInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantInDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRestaurantInDTOValid() {
        RestaurantInDTO restaurant = new RestaurantInDTO(1L, "Restaurant Name", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);

        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testRestaurantInDTOInvalidOwnerId() {
        RestaurantInDTO restaurant = new RestaurantInDTO(0L, "Restaurant Name", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);

        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);

        assertEquals(1, violations.size());
        assertEquals("Valid OwnerID required", violations.iterator().next().getMessage());
    }

    @Test
    public void testRestaurantInDTOInvalidName() {
        RestaurantInDTO restaurant = new RestaurantInDTO(1L, "", "restaurant@gmail.com", "9876543210", "Some address", new byte[0]);

        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);

        assertEquals(1, violations.size());
        assertEquals("Enter a valid name for restaurant", violations.iterator().next().getMessage());
    }

    @Test
    public void testRestaurantInDTOInvalidEmail() {
        RestaurantInDTO restaurant = new RestaurantInDTO(1L, "Restaurant Name", "invalid_email", "9876543210", "Some address", new byte[0]);
        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);

        assertEquals(2, violations.size());
    }

    @Test
    public void testRestaurantInDTOInvalidPhone() {
        RestaurantInDTO restaurant = new RestaurantInDTO(1L, "Restaurant Name", "restaurant@gmail.com", "12345", "Some address", new byte[0]);
        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);
        assertEquals(1, violations.size());
        assertEquals("Phone number should be valid", violations.iterator().next().getMessage());
    }

    @Test
    public void testRestaurantInDTOInvalidAddress() {
        RestaurantInDTO restaurant = new RestaurantInDTO(1L, "Restaurant Name", "restaurant@gmail.com", "9876543210", "", new byte[0]);
        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(restaurant);

        assertEquals(1, violations.size());
        assertEquals("Address for restaurant cannot be empty", violations.iterator().next().getMessage());
    }
}
