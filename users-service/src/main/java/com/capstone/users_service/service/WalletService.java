package com.capstone.users_service.service;

import com.capstone.users_service.entity.Wallet;

/**
 * WalletService for defining methods related to wallet table.
 */
public interface WalletService {
    Wallet findByUserId(long userId);
    String updateWallet(long userId, double amount);
    String rechargeWallet(long userId, double amount);
}
