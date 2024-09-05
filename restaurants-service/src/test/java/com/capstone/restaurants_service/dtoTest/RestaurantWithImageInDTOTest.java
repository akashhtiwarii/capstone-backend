package com.capstone.restaurants_service.dtoTest;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.RestaurantWithImageInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantWithImageInDTOTest {

    RestaurantInDTO restaurantInDTO = new RestaurantInDTO(1L, "John's Diner", "123 Main St", "987654321","abc");

    @Test
    void testEqualsSameObject() {
        MockMultipartFile image = new MockMultipartFile("image", "filename.png", "image/png", new byte[]{1, 2, 3});
        RestaurantWithImageInDTO dto1 = new RestaurantWithImageInDTO(restaurantInDTO, image);

        assertEquals(dto1, dto1);
    }

    @Test
    void testEqualsDifferentObjectSameData() {
        byte[] bytes = new byte[]{1, 2, 3};
        RestaurantInDTO restaurantInDTO1 = new RestaurantInDTO(1L, "John's Diner", "123 Main St", "987654321","abc");
        RestaurantInDTO restaurantInDTO2 = new RestaurantInDTO(1L, "John's Diner", "123 Main St", "987654321","abc");
        MockMultipartFile image1 = new MockMultipartFile("image", "filename.png", "image/png", bytes);

        RestaurantWithImageInDTO dto1 = new RestaurantWithImageInDTO(restaurantInDTO1, image1);
        RestaurantWithImageInDTO dto2 = new RestaurantWithImageInDTO(restaurantInDTO2, image1);

        assertEquals(dto1, dto2);
    }

    @Test
    void testNotEqualsDifferentRestaurant() {
        RestaurantInDTO restaurantInDTO1 = new RestaurantInDTO(1L, "John's Diner", "123 Main St", "987654321","abc");
        RestaurantInDTO restaurantInDTO2 = new RestaurantInDTO(2L, "Jane's Cafe", "abc@test.com", "987654321","abc");
        MockMultipartFile image = new MockMultipartFile("image", "filename.png", "image/png", new byte[]{1, 2, 3});

        RestaurantWithImageInDTO dto1 = new RestaurantWithImageInDTO(restaurantInDTO1, image);
        RestaurantWithImageInDTO dto2 = new RestaurantWithImageInDTO(restaurantInDTO2, image);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testNotEqualsDifferentImage() {
        MockMultipartFile image1 = new MockMultipartFile("image", "filename.png", "image/png", new byte[]{1, 2, 3});
        MockMultipartFile image2 = new MockMultipartFile("image", "differentfile.png", "image/png", new byte[]{4, 5, 6});

        RestaurantWithImageInDTO dto1 = new RestaurantWithImageInDTO(restaurantInDTO, image1);
        RestaurantWithImageInDTO dto2 = new RestaurantWithImageInDTO(restaurantInDTO, image2);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCodeConsistency() {
        MockMultipartFile image = new MockMultipartFile("image", "filename.png", "image/png", new byte[]{1, 2, 3});
        RestaurantWithImageInDTO dto = new RestaurantWithImageInDTO(restaurantInDTO, image);

        int hashCode1 = dto.hashCode();
        int hashCode2 = dto.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCodeEqualsObjects() {
        RestaurantInDTO restaurantInDTO1 = new RestaurantInDTO(2L, "Jane's Cafe", "abc@test.com", "987654321","abc");
        RestaurantInDTO restaurantInDTO2 = new RestaurantInDTO(2L, "Jane's Cafe", "abc@test.com", "987654321","abc");
        MockMultipartFile image1 = new MockMultipartFile("image", "filename.png", "image/png", new byte[]{1, 2, 3});

        RestaurantWithImageInDTO dto1 = new RestaurantWithImageInDTO(restaurantInDTO1, image1);
        RestaurantWithImageInDTO dto2 = new RestaurantWithImageInDTO(restaurantInDTO2, image1);

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
