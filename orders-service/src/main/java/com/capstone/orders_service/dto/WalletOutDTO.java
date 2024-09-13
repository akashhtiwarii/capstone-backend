package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object for representing details of a user's wallet.
 * <p>
 * It includes information such as the wallet's unique identifier,
 * the user associated with the wallet, and the current amount in the wallet.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOutDTO {

    /**
     * The unique identifier for the wallet.
     * <p>
     * This field uniquely identifies the wallet within the system.
     * </p>
     */
    private long walletId;

    /**
     * The unique identifier for the user who owns the wallet.
     * <p>
     * This field associates the wallet with a specific user.
     * </p>
     */
    private long userId;

    /**
     * The current amount of money in the wallet.
     * <p>
     * This field represents the balance available in the wallet.
     * </p>
     */
    private double amount;

    /**
     * Compares this {@code WalletOutDTO} instance with another object for equality.
     * <p>
     * Two {@code WalletOutDTO} instances are considered equal if they have the same wallet ID, user ID, and amount.
     * </p>
     *
     * @param o the object to be compared for equality with this instance
     * @return {@code true} if this instance is equal to the specified object; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WalletOutDTO that = (WalletOutDTO) o;
        return walletId == that.walletId && userId == that.userId && Double.compare(amount, that.amount) == 0;
    }

    /**
     * Returns a hash code value for this {@code WalletOutDTO} instance.
     * <p>
     * The hash code is computed based on the wallet ID, user ID, and amount to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(walletId, userId, amount);
    }
}
