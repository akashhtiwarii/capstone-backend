package com.capstone.users_service.dtoTest;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.dto.UserInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserInDTOTest {

    @Test
    public void testGetterAndSetter() {
        UserInDTO userInDTO = new UserInDTO();

        assertNull(userInDTO.getName());
        String name = "John Doe";
        userInDTO.setName(name);
        assertEquals(name, userInDTO.getName());

        assertNull(userInDTO.getPassword());
        String password = "password123";
        userInDTO.setPassword(password);
        assertEquals(password, userInDTO.getPassword());

        assertNull(userInDTO.getEmail());
        String email = "john.doe@gmail.com";
        userInDTO.setEmail(email);
        assertEquals(email, userInDTO.getEmail());

        assertNull(userInDTO.getPhone());
        String phone = "9876543210";
        userInDTO.setPhone(phone);
        assertEquals(phone, userInDTO.getPhone());

        assertNull(userInDTO.getRole());
        Role role = Role.USER;
        userInDTO.setRole(role);
        assertEquals(role, userInDTO.getRole());
    }

    @Test
    public void testEqualsAndHashcode() {
        String name = "John Doe";
        String password = "password123";
        String email = "john.doe@gmail.com";
        String phone = "9876543210";
        Role role = Role.USER;

        UserInDTO dto1 = buildUserInDTO(name, password, email, phone, role);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        UserInDTO dto2 = buildUserInDTO(name, password, email, phone, role);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserInDTO(name + " ", password, email, phone, role);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserInDTO(name, password + "1", email, phone, role);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserInDTO(name, password, email + " ", phone, role);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserInDTO(name, password, email, phone + " ", role);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserInDTO(name, password, email, phone, Role.OWNER);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new UserInDTO();
        dto2 = new UserInDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        UserInDTO dto = new UserInDTO();
        dto.setName("John Doe");
        dto.setPassword("password123");
        dto.setEmail("john.doe@gmail.com");
        dto.setPhone("9876543210");
        dto.setRole(Role.USER);

        assertEquals("UserInDTO(name=John Doe, password=password123, email=john.doe@gmail.com, phone=9876543210, role=USER)", dto.toString());
    }

    private UserInDTO buildUserInDTO(String name, String password, String email, String phone, Role role) {
        UserInDTO dto = new UserInDTO();
        dto.setName(name);
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setRole(role);
        return dto;
    }
}
