package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.mock;

public class FoodItemWithImageInDTOTest {

    @Test
    public void testSettersAndGetters() {
        FoodItemWithImageInDTO dto = new FoodItemWithImageInDTO();
        FoodItemInDTO foodItemInDTO = new FoodItemInDTO();
        MultipartFile image = mock(MultipartFile.class);

        dto.setFoodItem(foodItemInDTO);
        dto.setImage(image);

        assertEquals(foodItemInDTO, dto.getFoodItem());
        assertEquals(image, dto.getImage());
    }

    @Test
    public void testEqualsAndHashCode() {
        FoodItemInDTO foodItemInDTO1 = new FoodItemInDTO();
        MultipartFile image1 = mock(MultipartFile.class);
        FoodItemWithImageInDTO dto1 = new FoodItemWithImageInDTO(foodItemInDTO1, image1);

        FoodItemInDTO foodItemInDTO2 = new FoodItemInDTO();
        MultipartFile image2 = mock(MultipartFile.class);
        FoodItemWithImageInDTO dto2 = new FoodItemWithImageInDTO(foodItemInDTO2, image2);

        FoodItemWithImageInDTO dto3 = new FoodItemWithImageInDTO(foodItemInDTO1, image1);

        assertNotEquals(dto1, dto2);
        assertEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto3.hashCode());
    }
}
