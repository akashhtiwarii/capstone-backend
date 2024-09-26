package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
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

public class FoodItemInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEqualsMethod() {
        FoodItemInDTO foodItem1 = new FoodItemInDTO(1L, 1L, "Food", "Description", 9.99);
        FoodItemInDTO foodItem2 = new FoodItemInDTO(1L, 1L, "Food", "Description", 9.99);
        FoodItemInDTO foodItem3 = new FoodItemInDTO(2L, 1L, "Food2", "Description2", 12.99);
        assertEquals(foodItem1, foodItem2, "FoodItemInDTO objects with the same properties should be equal.");
        assertNotEquals(foodItem1, foodItem3, "FoodItemInDTO objects with different properties should not be equal.");
    }

    @Test
    void testHashCodeMethod() {
        FoodItemInDTO foodItem1 = new FoodItemInDTO(1L, 1L, "Food", "Description", 9.99);
        FoodItemInDTO foodItem2 = new FoodItemInDTO(1L, 1L, "Food", "Description", 9.99);

        assertEquals(foodItem1.hashCode(), foodItem2.hashCode(), "FoodItemInDTO objects with the same properties should have the same hash code.");
    }

    @Test
    void testValidFoodItemInDTO() {
        FoodItemInDTO foodItem = new FoodItemInDTO(1L, 1L, "Food", "Description", 9.99);

        Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validate(foodItem);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid FoodItemInDTO.");
    }

    @Test
    void testInvalidFoodItemInDTO() {
        FoodItemInDTO foodItem = new FoodItemInDTO(0L, 0L, "", null, -5.00);

        Set<ConstraintViolation<FoodItemInDTO>> violations = validator.validate(foodItem);

        assertTrue(violations.size() > 0, "There should be validation errors for an invalid FoodItemInDTO.");
    }

    @Test
    void testToString() {
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO(
                1L,
                2L,
                "Pizza",
                "Delicious cheese pizza",
                9.99
        );

        String expected = "FoodItemInDTO(loggedInOwnerId=1, categoryId=2, name=Pizza, description=Delicious cheese pizza, price=9.99)";

        assertEquals(expected, foodItemInDTO.toString());
    }
}
