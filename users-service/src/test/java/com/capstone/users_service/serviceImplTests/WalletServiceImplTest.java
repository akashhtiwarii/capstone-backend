package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.serviceImpl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUserIdWalletExists() {
        long userId = 1L;
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        Wallet result = walletService.findByUserId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findByUserIdWalletDoesNotExist() {
        long userId = 1L;
        when(walletRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> walletService.findByUserId(userId));
        verify(walletRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updateWalletWalletExists() {
        long userId = 1L;
        double newAmount = 200.0;
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAmount(100.0);
        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        String result = walletService.updateWallet(userId, newAmount);

        assertEquals("Wallet Updated Successfully", result);
        assertEquals(newAmount, wallet.getAmount());
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void updateWalletWalletDoesNotExist() {
        long userId = 1L;
        double newAmount = 200.0;
        when(walletRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> walletService.updateWallet(userId, newAmount));
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void updateWalletSaveFails() {
        long userId = 1L;
        double newAmount = 200.0;
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        when(walletRepository.findByUserId(userId)).thenReturn(wallet);
        doThrow(new RuntimeException("DB error")).when(walletRepository).save(wallet);

        assertThrows(RuntimeException.class, () -> walletService.updateWallet(userId, newAmount));
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void rechargeWalletWalletExists() {
        long userId = 1L;
        double rechargeAmount = 50.0;
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAmount(100.0);
        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        String result = walletService.rechargeWallet(userId, rechargeAmount);

        assertEquals("Wallet Recharged", result);
        assertEquals(150.0, wallet.getAmount());
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void rechargeWalletWalletDoesNotExist() {
        long userId = 1L;
        double rechargeAmount = 50.0;
        when(walletRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> walletService.rechargeWallet(userId, rechargeAmount));
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void rechargeWalletSaveFails() {
        long userId = 1L;
        double rechargeAmount = 50.0;
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAmount(100.0);
        when(walletRepository.findByUserId(userId)).thenReturn(wallet);
        doThrow(new RuntimeException("DB error")).when(walletRepository).save(wallet);

        assertThrows(RuntimeException.class, () -> walletService.rechargeWallet(userId, rechargeAmount));
        verify(walletRepository, times(1)).findByUserId(userId);
        verify(walletRepository, times(1)).save(wallet);
    }
}

