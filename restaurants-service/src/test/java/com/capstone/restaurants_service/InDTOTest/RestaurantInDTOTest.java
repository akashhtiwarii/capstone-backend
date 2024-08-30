package com.capstone.restaurants_service.InDTOTest;

import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RestaurantInDTOTest {

    @Test
    void testGettersAndSetters() {
        RestaurantInDTO dto = new RestaurantInDTO();
        dto.setOwnerId(1L);
        dto.setName("Test Restaurant");
        dto.setEmail("test@gmail.com");
        dto.setPhone("9876543210");
        dto.setAddress("123 Test Street");
        dto.setImage(new byte[]{1, 2, 3, 4, 5});

        assertEquals(1L, dto.getOwnerId());
        assertEquals("Test Restaurant", dto.getName());
        assertEquals("test@gmail.com", dto.getEmail());
        assertEquals("9876543210", dto.getPhone());
        assertEquals("123 Test Street", dto.getAddress());
        assertEquals(Arrays.toString(new byte[]{1, 2, 3, 4, 5}), Arrays.toString(dto.getImage()));
    }

    @Test
    void testEqualsAndHashCode() {
        RestaurantInDTO dto1 = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "9876543210", "123 Test Street", new byte[]{1, 2, 3, 4, 5});
        RestaurantInDTO dto2 = new RestaurantInDTO(1L, "Test Restaurant", "test@gmail.com", "9876543210", "123 Test Street", new byte[]{1, 2, 3, 4, 5});
        RestaurantInDTO dto3 = new RestaurantInDTO(2L, "Another Restaurant", "another@gmail.com", "9123456789", "456 Another Street", new byte[]{5, 4, 3, 2, 1});

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
