package com.capstone.restaurants_service.dtoTest.InDTOTest;

import com.capstone.restaurants_service.dto.InDTO.UpdateRestaurantInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UpdateRestaurantInDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testSettersAndGetters() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO();
        MultipartFile image = mock(MultipartFile.class);

        dto.setLoggedInOwnerId(1L);
        dto.setRestaurantId(2L);
        dto.setName("Test Restaurant");
        dto.setEmail("test@gmail.com");
        dto.setPhone("9876543210");
        dto.setAddress("Test Address");
        dto.setImage(image);

        assertEquals(1L, dto.getLoggedInOwnerId());
        assertEquals(2L, dto.getRestaurantId());
        assertEquals("Test Restaurant", dto.getName());
        assertEquals("test@gmail.com", dto.getEmail());
        assertEquals("9876543210", dto.getPhone());
        assertEquals("Test Address", dto.getAddress());
        assertEquals(image, dto.getImage());
    }

    @Test
    public void testValidation_Success() {
        MultipartFile image = mock(MultipartFile.class);
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(1L, 2L, "Test Restaurant", "test@gmail.com", "9876543210", "Test Address", image);

        Set<javax.validation.ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidation_Failure() {
        UpdateRestaurantInDTO dto = new UpdateRestaurantInDTO(-1L, -2L, "", "invalidemail", "12345", "", null);

        Set<javax.validation.ConstraintViolation<UpdateRestaurantInDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(7, violations.size());
    }

    @Test
    public void testEqualsAndHashCode() {
        MultipartFile image = mock(MultipartFile.class);
        UpdateRestaurantInDTO dto1 = new UpdateRestaurantInDTO(1L, 2L, "Test Restaurant", "test@gmail.com", "9876543210", "Test Address", image);
        UpdateRestaurantInDTO dto2 = new UpdateRestaurantInDTO(1L, 2L, "Test Restaurant", "test@gmail.com", "9876543210", "Test Address", image);
        UpdateRestaurantInDTO dto3 = new UpdateRestaurantInDTO(1L, 2L, "Another Restaurant", "another@gmail.com", "8765432109", "Another Address", null);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
