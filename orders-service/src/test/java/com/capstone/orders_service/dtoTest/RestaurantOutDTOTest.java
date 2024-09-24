package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.dto.RestaurantOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();

        assertEquals(0, restaurantOutDTO.getRestaurantId());
        long restaurantId = 100L;
        restaurantOutDTO.setRestaurantId(restaurantId);
        assertEquals(restaurantId, restaurantOutDTO.getRestaurantId());

        assertEquals(0, restaurantOutDTO.getOwnerId());
        long ownerId = 200L;
        restaurantOutDTO.setOwnerId(ownerId);
        assertEquals(ownerId, restaurantOutDTO.getOwnerId());

        assertNull(restaurantOutDTO.getName());
        String name = "Cafe Delight";
        restaurantOutDTO.setName(name);
        assertEquals(name, restaurantOutDTO.getName());

        assertNull(restaurantOutDTO.getEmail());
        String email = "cafe@delight.com";
        restaurantOutDTO.setEmail(email);
        assertEquals(email, restaurantOutDTO.getEmail());

        assertNull(restaurantOutDTO.getPhone());
        String phone = "123-456-7890";
        restaurantOutDTO.setPhone(phone);
        assertEquals(phone, restaurantOutDTO.getPhone());

        assertNull(restaurantOutDTO.getImage());
        byte[] image = new byte[]{1, 2, 3};
        restaurantOutDTO.setImage(image);
        assertArrayEquals(image, restaurantOutDTO.getImage());
    }

    @Test
    public void testConstructor() {
        long restaurantId = 100L;
        long ownerId = 200L;
        String name = "Bistro Place";
        String email = "contact@bistro.com";
        String phone = "987-654-3210";
        byte[] image = new byte[]{4, 5, 6};

        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO(restaurantId, ownerId, name, email, phone, image);

        assertEquals(restaurantId, restaurantOutDTO.getRestaurantId());
        assertEquals(ownerId, restaurantOutDTO.getOwnerId());
        assertEquals(name, restaurantOutDTO.getName());
        assertEquals(email, restaurantOutDTO.getEmail());
        assertEquals(phone, restaurantOutDTO.getPhone());
        assertArrayEquals(image, restaurantOutDTO.getImage());
    }

    @Test
    public void testImageClone() {
        byte[] image = new byte[]{7, 8, 9};
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();
        restaurantOutDTO.setImage(image);

        byte[] returnedImage = restaurantOutDTO.getImage();
        assertArrayEquals(image, returnedImage);

        returnedImage[0] = 10;
        assertNotEquals(image[0], returnedImage[0]);
    }

    @Test
    public void testEqualsAndHashCode() {
        long restaurantId = 100L;
        long ownerId = 200L;
        String name = "Restaurant ABC";
        String email = "abc@restaurant.com";
        String phone = "555-555-5555";
        byte[] image = new byte[]{10, 20, 30};

        RestaurantOutDTO restaurantOutDTO1 = new RestaurantOutDTO(restaurantId, ownerId, name, email, phone, image);
        RestaurantOutDTO restaurantOutDTO2 = new RestaurantOutDTO(restaurantId, ownerId, name, email, phone, image);

        assertEquals(restaurantOutDTO1, restaurantOutDTO2);
        assertEquals(restaurantOutDTO1.hashCode(), restaurantOutDTO2.hashCode());

        restaurantOutDTO2.setName("Changed Name");
        assertNotEquals(restaurantOutDTO1, restaurantOutDTO2);
        assertNotEquals(restaurantOutDTO1.hashCode(), restaurantOutDTO2.hashCode());
    }

    @Test
    public void testEqualsWithNullAndDifferentObject() {
        RestaurantOutDTO restaurantOutDTO = new RestaurantOutDTO();
        assertNotEquals(restaurantOutDTO, null);
        assertNotEquals(restaurantOutDTO, new Object());
    }
}
