package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.Restaurant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void testGettersAndSetters() {
        Restaurant restaurant = new Restaurant();
        byte[] image = {1, 2, 3, 4, 5};

        restaurant.setRestaurantId(1L);
        restaurant.setOwnerId(10L);
        restaurant.setName("The Culinary Place");
        restaurant.setEmail("culinary@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setAddress("123 Food Street");
        restaurant.setImage(image);

        assertEquals(1L, restaurant.getRestaurantId());
        assertEquals(10L, restaurant.getOwnerId());
        assertEquals("The Culinary Place", restaurant.getName());
        assertEquals("culinary@example.com", restaurant.getEmail());
        assertEquals("1234567890", restaurant.getPhone());
        assertEquals("123 Food Street", restaurant.getAddress());
        assertArrayEquals(image, restaurant.getImage());
    }

    @Test
    void testEqualsMethod() {
        byte[] image1 = {1, 2, 3};
        byte[] image2 = {1, 2, 3};
        byte[] image3 = {4, 5, 6};

        Restaurant restaurant1 = new Restaurant(1L, 10L, "Gourmet Kitchen", "gourmet@example.com", "9876543210", "45 Chef Lane", image1);
        Restaurant restaurant2 = new Restaurant(1L, 10L, "Gourmet Kitchen", "gourmet@example.com", "9876543210", "45 Chef Lane", image2);
        Restaurant restaurant3 = new Restaurant(2L, 11L, "Tasty Bites", "tasty@example.com", "1234567890", "67 Delicious Road", image3);

        assertEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1, restaurant3);
    }

    @Test
    void testEqualsMethodWithDifferentObjects() {
        Restaurant restaurant = new Restaurant(1L, 10L, "Urban Eats", "urban@example.com", "1112223333", "321 Flavor Avenue", new byte[]{1, 2, 3});

        assertNotEquals(null, restaurant);
        assertNotEquals(restaurant, new Object());
    }

    @Test
    void testHashCodeMethod() {
        byte[] image1 = {1, 2, 3};
        byte[] image2 = {1, 2, 3};
        byte[] image3 = {4, 5, 6};

        Restaurant restaurant1 = new Restaurant(1L, 10L, "Fusion Feast", "fusion@example.com", "5556667777", "88 Tasty Street", image1);
        Restaurant restaurant2 = new Restaurant(1L, 10L, "Fusion Feast", "fusion@example.com", "5556667777", "88 Tasty Street", image2);
        Restaurant restaurant3 = new Restaurant(3L, 12L, "Sweet Treats", "sweet@example.com", "4445556666", "99 Dessert Boulevard", image3);

        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());
        assertNotEquals(restaurant1.hashCode(), restaurant3.hashCode());
    }
}

