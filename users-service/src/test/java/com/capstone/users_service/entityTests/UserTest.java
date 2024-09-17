package com.capstone.users_service.entityTests;

import static org.junit.jupiter.api.Assertions.*;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.entity.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testGetterAndSetter() {
        User user = new User();

        assertEquals(0, user.getUserId());
        long userId = 123;
        user.setUserId(userId);
        assertEquals(userId, user.getUserId());

        assertNull(user.getName());
        String name = "name";
        user.setName(name);
        assertEquals(name, user.getName());

        assertNull(user.getEmail());
        String email = "name@gmail.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());

        assertNull(user.getPassword());
        String password = "password123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());

        assertNull(user.getPhone());
        String phone = "1234567890";
        user.setPhone(phone);
        assertEquals(phone, user.getPhone());

        assertNull(user.getRole());
        Role role = Role.OWNER;
        user.setRole(role);
        assertEquals(role, user.getRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = buildUser(123, "name", "name@gmail.com", "password123", "1234567890", Role.OWNER);
        User user2 = buildUser(123, "name", "name@gmail.com", "password123", "1234567890", Role.OWNER);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setUserId(124);
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());

        user2 = buildUser(123, "name2", "name@gmail.com", "password123", "1234567890", Role.OWNER);
        assertNotEquals(user1, user2);

        user2 = buildUser(123, "name", "email2@gmail.com", "password123", "1234567890", Role.OWNER);
        assertNotEquals(user1, user2);

        user2 = buildUser(123, "name", "name@gmail.com", "password123", "1234567890", Role.USER);
        assertNotEquals(user1, user2);

        User user3 = new User();
        User user4 = new User();
        assertEquals(user3, user4);
        assertEquals(user3.hashCode(), user4.hashCode());
    }

    @Test
    public void testToString() {
        User user = buildUser(123, "name", "name@gmail.com", "password123", "1234567890", Role.OWNER);
        String expectedString = "User(userId=123, name=name, email=name@gmail.com, password=password123, phone=1234567890, role=OWNER)";
        assertEquals(expectedString, user.toString());
    }

    private User buildUser(long userId, String name, String email, String password, String phone, Role role) {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRole(role);
        return user;
    }
}
