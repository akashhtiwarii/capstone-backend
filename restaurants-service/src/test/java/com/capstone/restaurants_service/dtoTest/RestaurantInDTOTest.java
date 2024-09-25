package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEqualsMethod() {
        RestaurantInDTO dto1 = new RestaurantInDTO(1L, "Restaurant A", "example@gmail.com", "9876543210", "address");
        RestaurantInDTO dto2 = new RestaurantInDTO(1L, "Restaurant A", "example@gmail.com", "9876543210", "address");
        RestaurantInDTO dto3 = new RestaurantInDTO(2L, "Restaurant B", "test@gmail.com", "8765432109", "address2");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testHashCodeMethod() {
        RestaurantInDTO dto1 = new RestaurantInDTO(1L, "Restaurant A", "example@gmail.com", "9876543210", "address");
        RestaurantInDTO dto2 = new RestaurantInDTO(1L, "Restaurant A", "example@gmail.com", "9876543210", "address");

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testValidRestaurantInDTO() {
        RestaurantInDTO dto = new RestaurantInDTO(1L, "Restaurant A", "example@gmail.com", "9876543210", "address");

        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRestaurantInDTO() {
        RestaurantInDTO dto = new RestaurantInDTO(0L, "", "invalidemail", "123456", "");

        Set<ConstraintViolation<RestaurantInDTO>> violations = validator.validate(dto);

        assertTrue(violations.size() > 0);
    }

    @Test
    void testToString() {
        RestaurantInDTO restaurantInDTO = new RestaurantInDTO(
                1L,
                "Pasta Palace",
                "contact@pasta.com",
                "9876543210",
                "123 Noodle St"
        );

        String expected = "RestaurantInDTO(ownerId=1, name=Pasta Palace, email=contact@pasta.com, phone=9876543210, address=123 Noodle St)";

        assertEquals(expected, restaurantInDTO.toString());
    }
}
