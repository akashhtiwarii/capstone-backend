package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WalletServiceImpl for implementing methods of WalletService.
 */
@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet findByUserId(long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new ResourceNotFoundException("User not present");
        }
        return wallet;
    }

    @Override
    public String updateWallet(long userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new ResourceNotFoundException("User not present");
        }
        wallet.setAmount(amount);
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            throw new RuntimeException("An Unexpected Error Occurred.");
        }
        return "Wallet Updated Successfully";
    }

    @Override
    public String rechargeWallet(long userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new ResourceNotFoundException("No User Present");
        }
        double walletBalance = wallet.getAmount();
        wallet.setAmount(walletBalance + amount);
        walletRepository.save(wallet);
        return "Wallet Recharged";
    }
}
