package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing the {@link Wallet} entity.
 * Provides methods to perform CRUD operations and custom queries
 * on the {@code wallet} table in the database.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    /**
     * Retrieves a {@link Wallet} entity by its unique identifier.
     *
     * @param walletId the ID of the wallet to be retrieved
     * @return the {@link Wallet} entity with the specified ID, or {@code null} if no such entity exists
     */
    Wallet findById(long walletId);

    /**
     * Retrieves a {@link Wallet} entity associated with the specified user ID.
     *
     * @param userId the ID of the user whose wallet is to be retrieved
     * @return the {@link Wallet} entity associated with the specified user ID, or {@code null} if no such entity exists
     */
    Wallet findByUserId(long userId);
}
