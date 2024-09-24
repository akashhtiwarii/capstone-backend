package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.UpdateProfileInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateProfileInDTOTest {

    @Test
    public void testGetterAndSetter() {
        UpdateProfileInDTO updateProfileInDTO = new UpdateProfileInDTO();

        assertNull(updateProfileInDTO.getName());
        String name = "name";
        updateProfileInDTO.setName(name);
        assertEquals(name, updateProfileInDTO.getName());

        assertNull(updateProfileInDTO.getEmail());
        String email = "email@gmail.com";
        updateProfileInDTO.setEmail(email);
        assertEquals(email, updateProfileInDTO.getEmail());

        assertNull(updateProfileInDTO.getPhone());
        String phone = "9876543210";
        updateProfileInDTO.setPhone(phone);
        assertEquals(phone, updateProfileInDTO.getPhone());
    }

    @Test
    public void testEqualsAndHashcode() {
        String name = "name";
        String email = "email@gmail.com";
        String phone = "9876543210";

        UpdateProfileInDTO dto1 = buildUpdateProfileInDTO(name, email, phone);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        UpdateProfileInDTO dto2 = buildUpdateProfileInDTO(name, email, phone);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateProfileInDTO(name + " ", email, phone);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateProfileInDTO(name, email + " ", phone);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateProfileInDTO(name, email, phone + " ");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new UpdateProfileInDTO();
        dto2 = new UpdateProfileInDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        UpdateProfileInDTO dto = new UpdateProfileInDTO();
        dto.setName("name");
        dto.setEmail("email@gmail.com");
        dto.setPhone("9876543210");

        assertEquals("UpdateProfileInDTO(name=name, email=email@gmail.com, phone=9876543210)", dto.toString());
    }

    private UpdateProfileInDTO buildUpdateProfileInDTO(String name, String email, String phone) {
        UpdateProfileInDTO dto = new UpdateProfileInDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPhone(phone);
        return dto;
    }
}
