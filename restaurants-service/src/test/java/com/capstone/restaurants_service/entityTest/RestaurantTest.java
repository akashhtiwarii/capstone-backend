package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    @Test
    public void testGetterAndSetter() {
        Restaurant restaurant = new Restaurant();

        assertEquals(0, restaurant.getRestaurantId());
        restaurant.setRestaurantId(1);
        assertEquals(1, restaurant.getRestaurantId());

        assertEquals(0, restaurant.getOwnerId());
        restaurant.setOwnerId(2);
        assertEquals(2, restaurant.getOwnerId());

        assertNull(restaurant.getName());
        restaurant.setName("Restaurant Name");
        assertEquals("Restaurant Name", restaurant.getName());

        assertNull(restaurant.getEmail());
        restaurant.setEmail("email@example.com");
        assertEquals("email@example.com", restaurant.getEmail());

        assertNull(restaurant.getPhone());
        restaurant.setPhone("1234567890");
        assertEquals("1234567890", restaurant.getPhone());

        assertNull(restaurant.getAddress());
        restaurant.setAddress("address");
        assertEquals("address", restaurant.getAddress());

        assertNull(restaurant.getImage());
        byte[] image = {1, 2, 3};
        restaurant.setImage(image);
        assertArrayEquals(image, restaurant.getImage());
    }

    @Test
    public void testToString() {
        Restaurant restaurant = new Restaurant();

        restaurant.setRestaurantId(1);
        restaurant.setOwnerId(2);
        restaurant.setName("Restaurant Name");
        restaurant.setEmail("email@example.com");
        restaurant.setPhone("1234567890");
        restaurant.setAddress("address");
        restaurant.setImage(new byte[]{1, 2, 3});

        assertEquals("Restaurant(restaurantId=1, ownerId=2, name=Restaurant Name, email=email@example.com, " +
                "phone=1234567890, address=address, image=[1, 2, 3])", restaurant.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        Restaurant restaurant1 = buildRestaurant(1, 2, "Restaurant Name", "email@example.com", "1234567890", "address", new byte[]{1, 2, 3});

        assertEquals(restaurant1, restaurant1);
        assertEquals(restaurant1.hashCode(), restaurant1.hashCode());

        assertNotEquals(restaurant1, new Object());

        Restaurant restaurant2 = buildRestaurant(1, 2, "Restaurant Name", "email@example.com", "1234567890", "address", new byte[]{1, 2, 3});
        assertEquals(restaurant1, restaurant2);
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(2, 2, "Restaurant Name", "email@example.com", "1234567890", "address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 3, "Restaurant Name", "email@example.com", "1234567890", "address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 2, "Different Name", "email@example.com", "1234567890", "address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 2, "Restaurant Name", "different@example.com", "1234567890", "address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 2, "Restaurant Name", "email@example.com", "9876543210", "address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 2, "Restaurant Name", "email@example.com", "1234567890", "Different Address", new byte[]{1, 2, 3});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        restaurant2 = buildRestaurant(1, 2, "Restaurant Name", "email@example.com", "1234567890", "address", new byte[]{4, 5, 6});
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        Restaurant restaurant3 = new Restaurant();
        Restaurant restaurant4 = new Restaurant();
        assertEquals(restaurant3, restaurant4);
        assertEquals(restaurant3.hashCode(), restaurant4.hashCode());
    }

    private Restaurant buildRestaurant(long restaurantId, long ownerId, String name, String email, String phone, String address, byte[] image) {
        Restaurant restaurant = new Restaurant();

        restaurant.setRestaurantId(restaurantId);
        restaurant.setOwnerId(ownerId);
        restaurant.setName(name);
        restaurant.setEmail(email);
        restaurant.setPhone(phone);
        restaurant.setAddress(address);
        restaurant.setImage(image);

        return restaurant;
    }
}