package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link WalletService} for managing wallet-related operations.
 * This service provides methods for finding, updating, and recharging user wallets.
 */
@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
    /**
     * Repository for accessing the {@link Wallet} table using JPA methods.
     */
    @Autowired
    private WalletRepository walletRepository;

    /**
     * Finds the wallet associated with a given user ID.
     *
     * @param userId the ID of the user whose wallet is to be retrieved
     * @return the {@link Wallet} entity associated with the specified user ID
     * @throws ResourceNotFoundException if no wallet is found for the specified user ID
     */
    @Override
    public Wallet findByUserId(final long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            log.error("Wallet not found for userId: {}", userId);
            throw new ResourceNotFoundException("User not present");
        }
        return wallet;
    }

    /**
     * Updates the wallet amount for a given user ID.
     *
     * @param userId the ID of the user whose wallet amount is to be updated
     * @param amount the new amount to set in the wallet
     * @return a {@link String} message indicating the result of the update operation
     * @throws ResourceNotFoundException if no wallet is found for the specified user ID
     * @throws RuntimeException if an unexpected error occurs while saving the wallet
     */
    @Override
    public String updateWallet(final long userId, final double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            log.error("Wallet not found for userId: {}", userId);
            throw new ResourceNotFoundException("User not present");
        }
        wallet.setAmount(amount);
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            log.error("Failed to update wallet for userId: {}. Error: {}", userId, e.getMessage());
            throw new RuntimeException("An Unexpected Error Occurred.");
        }
        return "Wallet Updated Successfully";
    }

    /**
     * Recharges the wallet amount for a given user ID by adding the specified amount.
     *
     * @param userId the ID of the user whose wallet is to be recharged
     * @param amount the amount to add to the wallet
     * @return a {@link String} message indicating the result of the recharge operation
     * @throws ResourceNotFoundException if no wallet is found for the specified user ID
     */
    @Override
    public String rechargeWallet(final long userId, final double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            log.error("Wallet not found for userId: {}", userId);
            throw new ResourceNotFoundException("No User Present");
        }
        double walletBalance = wallet.getAmount();
        wallet.setAmount(walletBalance + amount);
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            log.error("Failed to recharge wallet for userId: {}. Error: {}", userId, e.getMessage());
            throw new RuntimeException("An Unexpected Error Occurred.");
        }
        return "Wallet Recharged";
    }
}
