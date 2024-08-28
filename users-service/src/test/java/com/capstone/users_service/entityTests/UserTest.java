package com.capstone.users_service.entityTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User(1L, "John Doe", "john.doe@gmail.com", "password123", "9234567890", Role.USER);
        user2 = new User(1L, "John Doe", "john.doe@gmail.com", "password123", "9234567890", Role.USER);
    }

    @Test
    public void testHashCode() {
        assertEquals(user1.hashCode(), user2.hashCode());
        user2.setUserId(2L);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {

        assertEquals(1L, user1.getUserId());
        assertEquals("John Doe", user1.getName());
        assertEquals("john.doe@gmail.com", user1.getEmail());
        assertEquals("password123", user1.getPassword());
        assertEquals("9234567890", user1.getPhone());
        assertEquals(Role.USER, user1.getRole());

        user1.setUserId(2L);
        assertEquals(2L, user1.getUserId());

        user1.setName("Jane Doe");
        assertEquals("Jane Doe", user1.getName());

        user1.setEmail("jane.doe@gmail.com");
        assertEquals("jane.doe@gmail.com", user1.getEmail());

        user1.setPassword("newpassword");
        assertEquals("newpassword", user1.getPassword());

        user1.setPhone("9987654321");
        assertEquals("9987654321", user1.getPhone());

        user1.setRole(Role.OWNER);
        assertEquals(Role.OWNER, user1.getRole());
    }

    @Test
    public void testEquals() {
        User user1 = new User(1L, "John Doe", "john@gmail.com", "password123", "9234567890", Role.USER);
        User user2 = new User(1L, "John Doe", "john@gmail.com", "password123", "9234567890", Role.USER);
        User user3 = new User(2L, "John Doe", "john@gmail.com", "password123", "9234567890", Role.USER);
        User user4 = new User(1L, "Jane Doe", "john@gmail.com", "password123", "9234567890", Role.USER);
        assertTrue(user1.equals(user1));
        assertFalse(user1.equals(user3));
        assertFalse(user1.equals(user4));
        assertTrue(user1.equals(user2));
    }
}
