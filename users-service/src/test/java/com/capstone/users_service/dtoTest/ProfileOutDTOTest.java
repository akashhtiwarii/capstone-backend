package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.ProfileOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        ProfileOutDTO profileOutDTO = new ProfileOutDTO();

        assertNull(profileOutDTO.getName());
        String name = "John Doe";
        profileOutDTO.setName(name);
        assertEquals(name, profileOutDTO.getName());

        assertNull(profileOutDTO.getEmail());
        String email = "johndoe@example.com";
        profileOutDTO.setEmail(email);
        assertEquals(email, profileOutDTO.getEmail());

        assertNull(profileOutDTO.getPhone());
        String phone = "1234567890";
        profileOutDTO.setPhone(phone);
        assertEquals(phone, profileOutDTO.getPhone());

        assertEquals(0.0, profileOutDTO.getWalletAmount());
        double walletAmount = 100.50;
        profileOutDTO.setWalletAmount(walletAmount);
        assertEquals(walletAmount, profileOutDTO.getWalletAmount());
    }

    @Test
    public void testEqualsAndHashCode() {
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phone = "1234567890";
        double walletAmount = 100.50;

        ProfileOutDTO dto1 = buildProfileOutDTO(name, email, phone, walletAmount);
        ProfileOutDTO dto2 = buildProfileOutDTO(name, email, phone, walletAmount);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setName("Jane Doe");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        ProfileOutDTO profileOutDTO = new ProfileOutDTO();

        String name = "John Doe";
        profileOutDTO.setName(name);

        String email = "johndoe@example.com";
        profileOutDTO.setEmail(email);

        String phone = "1234567890";
        profileOutDTO.setPhone(phone);

        double walletAmount = 100.50;
        profileOutDTO.setWalletAmount(walletAmount);

        assertEquals("ProfileOutDTO(name=John Doe, email=johndoe@example.com, phone=1234567890, walletAmount=100.5)", profileOutDTO.toString());
    }

    private ProfileOutDTO buildProfileOutDTO(String name, String email, String phone, double walletAmount) {
        return new ProfileOutDTO(name, email, phone, walletAmount);
    }
}
