package com.capstone.restaurants_service.dtoTest;


import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UpdateRestaurantInDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidation_ValidDTO_ShouldPass() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(1L, 1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address");
        Set<javax.validation.ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidation_InvalidEmail_ShouldFail() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(1L, 1L, "Restaurant Name", "test@example.com", "9876543210", "Restaurant Address");
        Set<javax.validation.ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1)
                .extracting("message")
                .contains("Enter a valid email ID for restaurant");
    }

    @Test
    public void testValidation_InvalidPhone_ShouldFail() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(1L, 1L, "Restaurant Name", "test@gmail.com", "1234567890", "Restaurant Address");
        Set<javax.validation.ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1)
                .extracting("message")
                .contains("Phone number should be valid");
    }

    @Test
    public void testGetterSetter() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO();
        dto.setLoggedInOwnerId(1L);
        dto.setRestaurantId(2L);
        dto.setName("Restaurant Name");
        dto.setEmail("test@gmail.com");
        dto.setPhone("9876543210");
        dto.setAddress("Restaurant Address");

        assertThat(dto.getLoggedInOwnerId()).isEqualTo(1L);
        assertThat(dto.getRestaurantId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Restaurant Name");
        assertThat(dto.getEmail()).isEqualTo("test@gmail.com");
        assertThat(dto.getPhone()).isEqualTo("9876543210");
        assertThat(dto.getAddress()).isEqualTo("Restaurant Address");
    }

    @Test
    public void testEqualsAndHashCode() {
        UpdateRestaurantInDTO dto1 = new UpdateRestaurantInDTO(1L, 1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address");
        UpdateRestaurantInDTO dto2 = new UpdateRestaurantInDTO(1L, 1L, "Restaurant Name", "test@gmail.com", "9876543210", "Restaurant Address");
        UpdateRestaurantInDTO dto3 = new UpdateRestaurantInDTO(2L, 1L, "Different Restaurant", "test@gmail.com", "9876543210", "Restaurant Address");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);

        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }
}
