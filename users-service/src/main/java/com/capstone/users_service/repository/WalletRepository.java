package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * walletRepository to connect with wallet table.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
