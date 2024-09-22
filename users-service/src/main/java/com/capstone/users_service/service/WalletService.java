package com.capstone.users_service.service;

import com.capstone.users_service.entity.Wallet;

/**
 * Service interface for managing {@link Wallet} entities.
 * Defines methods for performing operations related to user wallets.
 */
public interface WalletService {

    /**
     * Retrieves the {@link Wallet} associated with a specific user ID.
     *
     * @param userId the ID of the user whose wallet is to be retrieved
     * @return the {@link Wallet} entity associated with the specified user ID, or {@code null} if no wallet is found
     */
    Wallet findByUserId(long userId);

    /**
     * Updates the balance of a user's wallet.
     *
     * @param userId the ID of the user whose wallet balance is to be updated
     * @param amount the amount to update the wallet balance by (can be positive or negative)
     * @return a {@link String} message indicating the result of the update operation
     */
    String updateWallet(long userId, double amount);

    /**
     * Recharges a user's wallet by adding a specified amount.
     *
     * @param userId the ID of the user whose wallet is to be recharged
     * @param amount the amount to be added to the wallet
     * @return a {@link String} message indicating the result of the recharge operation
     */
    String rechargeWallet(long userId, double amount);
}
