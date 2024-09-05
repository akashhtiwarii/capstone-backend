package com.capstone.users_service.entityTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;

    @BeforeEach
    void setUp() {
        restaurant1 = new Restaurant(1L, 2L, "Test Restaurant", "test@gmail.com",
                "9234567890", "Saket Colony", new byte[]{1, 2, 3, 4, 5});

        restaurant2 = new Restaurant(1L, 2L, "Test Restaurant", "test@gmail.com",
                "9234567890", "Saket Colony", new byte[]{1, 2, 3, 4, 5});

        restaurant3 = new Restaurant(3L, 4L, "Different Restaurant", "different@gmail.com",
                "0987654321", "Hanuman Nagar", new byte[]{5, 4, 3, 2, 1});
    }

    @Test
    void testGettersAndSetters() {
        restaurant1.setRestaurantId(10L);
        restaurant1.setOwnerId(20L);
        restaurant1.setName("Modified Restaurant");
        restaurant1.setEmail("modified@gmail.com");
        restaurant1.setPhone("9122334455");
        restaurant1.setAddress("Malviya Nagar");
        restaurant1.setImage(new byte[]{9, 8, 7, 6, 5});

        assertEquals(10L, restaurant1.getRestaurantId());
        assertEquals(20L, restaurant1.getOwnerId());
        assertEquals("Modified Restaurant", restaurant1.getName());
        assertEquals("modified@gmail.com", restaurant1.getEmail());
        assertEquals("9122334455", restaurant1.getPhone());
        assertEquals("Malviya Nagar", restaurant1.getAddress());
        assertTrue(Arrays.equals(new byte[]{9, 8, 7, 6, 5}, restaurant1.getImage()));
    }

    @Test
    void testEquals() {
        assertEquals(restaurant1, restaurant1);
        assertEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1, restaurant3);
        assertNotEquals(restaurant1, null);
    }

    @Test
    void testHashCode() {
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());
        assertNotEquals(restaurant1.hashCode(), restaurant3.hashCode());
    }
}
