package com.capstone.users_service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Represents a wallet entity mapped to the 'wallet' table in the database.
 * <p>
 * This class is used to persist and retrieve wallet information, including details such as wallet ID, user ID,
 * and the amount of money in the wallet.
 * </p>
 */
@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    /**
     * Unique identifier for the wallet entity.
     * This field is mapped to the 'wallet_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private long walletId;

    /**
     * Identifier for the user associated with this wallet.
     * This field is mapped to the 'user_id' column in the database.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * The amount of money in the wallet.
     * This field is mapped to the 'amount' column in the database.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Compares this wallet entity with another object for equality.
     * <p>
     * Two wallet entities are considered equal if they have the same wallet ID, user ID, and amount.
     * </p>
     * @param o the object to be compared
     * @return true if this wallet is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wallet wallet = (Wallet) o;
        return walletId == wallet.walletId && userId == wallet.userId && Double.compare(amount, wallet.amount) == 0;
    }

    /**
     * Returns a hash code value for this wallet entity.
     * <p>
     * The hash code is computed based on the wallet ID, user ID, and amount.
     * </p>
     * @return a hash code value for this wallet entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(walletId, userId, amount);
    }
}
