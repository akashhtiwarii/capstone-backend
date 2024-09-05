package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.UpdateFoodItemInDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateFoodItemInDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEqualsMethod() {
        MultipartFile image = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
        UpdateFoodItemInDTO dto1 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.0, image);
        UpdateFoodItemInDTO dto2 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.0, image);
        UpdateFoodItemInDTO dto3 = new UpdateFoodItemInDTO(3L, 4L, "Different Food", "Another Description", 20.0, null);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testHashCodeMethod() {
        MultipartFile image = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
        UpdateFoodItemInDTO dto1 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.0, image);
        UpdateFoodItemInDTO dto2 = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.0, image);

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testValidUpdateFoodItemInDTO() {
        MultipartFile image = new MockMultipartFile("file", "image.jpg", "image/jpeg", new byte[0]);
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(1L, 2L, "Food Item", "Description", 10.0, image);

        Set<ConstraintViolation<UpdateFoodItemInDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUpdateFoodItemInDTO() {
        UpdateFoodItemInDTO dto = new UpdateFoodItemInDTO(0L, 0L, "", null, -10.0, null);

        Set<ConstraintViolation<UpdateFoodItemInDTO>> violations = validator.validate(dto);

        assertTrue(violations.size() > 0);
    }
}
