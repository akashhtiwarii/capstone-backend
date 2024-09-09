package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOutDTO {
    private long walletId;
    private long userId;
    private double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletOutDTO that = (WalletOutDTO) o;
        return walletId == that.walletId && userId == that.userId && Double.compare(amount, that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, userId, amount);
    }
}
