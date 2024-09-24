package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.dto.WalletOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WalletOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        WalletOutDTO walletOutDTO = new WalletOutDTO();

        assertEquals(0, walletOutDTO.getWalletId());
        long walletId = 1L;
        walletOutDTO.setWalletId(walletId);
        assertEquals(walletId, walletOutDTO.getWalletId());

        assertEquals(0, walletOutDTO.getUserId());
        long userId = 2L;
        walletOutDTO.setUserId(userId);
        assertEquals(userId, walletOutDTO.getUserId());

        assertEquals(0, walletOutDTO.getAmount(), 0.0);
        double amount = 100.50;
        walletOutDTO.setAmount(amount);
        assertEquals(amount, walletOutDTO.getAmount(), 0.0);
    }

    @Test
    public void testToString() {
        WalletOutDTO walletOutDTO = new WalletOutDTO();

        long walletId = 1L;
        walletOutDTO.setWalletId(walletId);

        long userId = 2L;
        walletOutDTO.setUserId(userId);

        double amount = 100.50;
        walletOutDTO.setAmount(amount);

        assertEquals("WalletOutDTO(walletId=1, userId=2, amount=100.5)", walletOutDTO.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        long walletId = 1L;
        long userId = 2L;
        double amount = 100.50;

        WalletOutDTO walletOutDTO1 = buildWalletOutDTO(walletId, userId, amount);

        assertEquals(walletOutDTO1, walletOutDTO1);
        assertEquals(walletOutDTO1.hashCode(), walletOutDTO1.hashCode());

        assertNotEquals(walletOutDTO1, new Object());

        WalletOutDTO walletOutDTO2 = buildWalletOutDTO(walletId, userId, amount);
        assertEquals(walletOutDTO1, walletOutDTO2);
        assertEquals(walletOutDTO1.hashCode(), walletOutDTO2.hashCode());

        walletOutDTO2 = buildWalletOutDTO(walletId + 1, userId, amount);
        assertNotEquals(walletOutDTO1, walletOutDTO2);
        assertNotEquals(walletOutDTO1.hashCode(), walletOutDTO2.hashCode());

        walletOutDTO2 = buildWalletOutDTO(walletId, userId + 1, amount);
        assertNotEquals(walletOutDTO1, walletOutDTO2);
        assertNotEquals(walletOutDTO1.hashCode(), walletOutDTO2.hashCode());

        walletOutDTO2 = buildWalletOutDTO(walletId, userId, amount + 10);
        assertNotEquals(walletOutDTO1, walletOutDTO2);
        assertNotEquals(walletOutDTO1.hashCode(), walletOutDTO2.hashCode());

        walletOutDTO1 = new WalletOutDTO();
        walletOutDTO2 = new WalletOutDTO();
        assertEquals(walletOutDTO1, walletOutDTO2);
        assertEquals(walletOutDTO1.hashCode(), walletOutDTO2.hashCode());
    }

    private WalletOutDTO buildWalletOutDTO(long walletId, long userId, double amount) {
        WalletOutDTO walletOutDTO = new WalletOutDTO();
        walletOutDTO.setWalletId(walletId);
        walletOutDTO.setUserId(userId);
        walletOutDTO.setAmount(amount);
        return walletOutDTO;
    }
}
