package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.Enum.Role;
import com.capstone.orders_service.dto.UserOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        UserOutDTO userOutDTO = new UserOutDTO();

        assertEquals(0, userOutDTO.getUserId());
        long userId = 1L;
        userOutDTO.setUserId(userId);
        assertEquals(userId, userOutDTO.getUserId());

        assertNull(userOutDTO.getEmail());
        String email = "test@example.com";
        userOutDTO.setEmail(email);
        assertEquals(email, userOutDTO.getEmail());

        assertNull(userOutDTO.getName());
        String name = "John Doe";
        userOutDTO.setName(name);
        assertEquals(name, userOutDTO.getName());

        assertNull(userOutDTO.getPhone());
        String phone = "1234567890";
        userOutDTO.setPhone(phone);
        assertEquals(phone, userOutDTO.getPhone());

        assertNull(userOutDTO.getRole());
        Role role = Role.OWNER;
        userOutDTO.setRole(role);
        assertEquals(role, userOutDTO.getRole());
    }

    @Test
    public void testToString() {
        UserOutDTO userOutDTO = new UserOutDTO();

        long userId = 1L;
        userOutDTO.setUserId(userId);

        String email = "test@example.com";
        userOutDTO.setEmail(email);

        String name = "John Doe";
        userOutDTO.setName(name);

        String phone = "1234567890";
        userOutDTO.setPhone(phone);

        Role role = Role.OWNER;
        userOutDTO.setRole(role);

        assertEquals("UserOutDTO(userId=1, email=test@example.com, name=John Doe, phone=1234567890, role=OWNER)", userOutDTO.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 1L;
        String email = "test@example.com";
        String name = "John Doe";
        String phone = "1234567890";
        Role role = Role.OWNER;

        UserOutDTO userOutDTO1 = buildUserOutDTO(userId, email, name, phone, role);

        assertEquals(userOutDTO1, userOutDTO1);
        assertEquals(userOutDTO1.hashCode(), userOutDTO1.hashCode());

        assertNotEquals(userOutDTO1, new Object());

        UserOutDTO userOutDTO2 = buildUserOutDTO(userId, email, name, phone, role);
        assertEquals(userOutDTO1, userOutDTO2);
        assertEquals(userOutDTO1.hashCode(), userOutDTO2.hashCode());

        userOutDTO2 = buildUserOutDTO(userId + 1, email, name, phone, role);
        assertNotEquals(userOutDTO1, userOutDTO2);
        assertNotEquals(userOutDTO1.hashCode(), userOutDTO2.hashCode());

        userOutDTO2 = buildUserOutDTO(userId, email + "diff", name, phone, role);
        assertNotEquals(userOutDTO1, userOutDTO2);
        assertNotEquals(userOutDTO1.hashCode(), userOutDTO2.hashCode());

        userOutDTO1 = new UserOutDTO();
        userOutDTO2 = new UserOutDTO();
        assertEquals(userOutDTO1, userOutDTO2);
        assertEquals(userOutDTO1.hashCode(), userOutDTO2.hashCode());
    }

    private UserOutDTO buildUserOutDTO(long userId, String email, String name, String phone, Role role) {
        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(userId);
        userOutDTO.setEmail(email);
        userOutDTO.setName(name);
        userOutDTO.setPhone(phone);
        userOutDTO.setRole(role);
        return userOutDTO;
    }
}

