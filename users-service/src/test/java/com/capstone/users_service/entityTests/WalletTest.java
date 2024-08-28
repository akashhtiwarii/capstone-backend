package com.capstone.users_service.entityTests;

import com.capstone.users_service.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void setUp() {
        wallet1 = new Wallet(1L, 1L, 1000.0);
        wallet2 = new Wallet(1L, 1L, 1000.0);
    }

    @Test
    public void testHashCode() {
        assertEquals(wallet1.hashCode(), wallet2.hashCode());
        wallet2.setUserId(2L);
        assertNotEquals(wallet1.hashCode(), wallet2.hashCode());
    }

    @Test
    public void testGettersAndSetter() {
        assertEquals(1L, wallet1.getWalletId());
        assertEquals(1L, wallet1.getUserId());
        assertEquals(1000.0, wallet1.getAmount());

        wallet1.setWalletId(2L);
        assertEquals(2L, wallet1.getWalletId());
        wallet1.setUserId(2L);
        assertEquals(2L, wallet1.getUserId());
        wallet1.setAmount(2000.0);
        assertEquals(2000.0, wallet1.getAmount());
    }

    @Test
    public void testEquals() {
        Wallet wallet1 = new Wallet(1L, 1001L, 250.75);
        Wallet wallet2 = new Wallet(1L, 1001L, 250.75);
        Wallet wallet3 = new Wallet(2L, 1001L, 250.75);
        Wallet wallet4 = new Wallet(1L, 1002L, 250.75);
        Wallet wallet5 = new Wallet(1L, 1001L, 300.00);
        assertTrue(wallet1.equals(wallet1));
        assertFalse(wallet1.equals(wallet3));
        assertFalse(wallet1.equals(wallet4));
        assertFalse(wallet1.equals(wallet5));
        assertTrue(wallet1.equals(wallet2));
    }

}
