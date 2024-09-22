package com.capstone.users_service.entityTests;

import com.capstone.users_service.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {

    @Test
    public void testGetterAndSetter() {
        Wallet wallet = new Wallet();

        assertEquals(0L, wallet.getWalletId());
        long walletId = 1L;
        wallet.setWalletId(walletId);
        assertEquals(walletId, wallet.getWalletId());

        assertEquals(0L, wallet.getUserId());
        long userId = 101L;
        wallet.setUserId(userId);
        assertEquals(userId, wallet.getUserId());

        assertEquals(0.0, wallet.getAmount());
        double amount = 500.75;
        wallet.setAmount(amount);
        assertEquals(amount, wallet.getAmount());
    }

    @Test
    public void testEqualsAndHashCode() {
        Wallet wallet1 = buildWallet(1L, 101L, 500.75);
        Wallet wallet2 = buildWallet(1L, 101L, 500.75);

        assertEquals(wallet1, wallet2);
        assertEquals(wallet1.hashCode(), wallet2.hashCode());

        wallet2.setWalletId(2L);
        assertNotEquals(wallet1, wallet2);
        assertNotEquals(wallet1.hashCode(), wallet2.hashCode());

        wallet2 = buildWallet(1L, 102L, 500.75);
        assertNotEquals(wallet1, wallet2);

        wallet2 = buildWallet(1L, 101L, 600.50);
        assertNotEquals(wallet1, wallet2);

        Wallet wallet3 = new Wallet();
        Wallet wallet4 = new Wallet();
        assertEquals(wallet3, wallet4);
        assertEquals(wallet3.hashCode(), wallet4.hashCode());
    }

    private Wallet buildWallet(long walletId, long userId, double amount) {
        return new Wallet(walletId, userId, amount);
    }
}