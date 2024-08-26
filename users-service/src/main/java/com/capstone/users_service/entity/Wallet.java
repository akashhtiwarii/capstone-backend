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
 * Wallet Entity mapping with wallet table.
 */
@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    /**
     walletId for connecting wallet_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private long walletId;

    /**
     userId for connecting userId field in database using ORM.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     amount for connecting amount field in database using ORM.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Override equals method for testing.
     * @param o object
     * @return true or false based on conditions
     */
    @Override
    public boolean equals(Object o) {
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
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(walletId, userId, amount);
    }
}
