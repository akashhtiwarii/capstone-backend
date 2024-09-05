package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddToCartInDTO {
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;
    @NotNull(message = "Valid Restaurant ID required")
    @Min(value = 1, message = "Valid Restaurant ID required")
    private long restaurantId;
    @NotNull(message = "Valid Food ID required")
    @Min(value = 1, message = "Valid Food ID required")
    private long foodId;
    @NotNull(message = "Valid Quantity Required")
    @Positive(message = "Valid Quantity Required")
    private int quantity;
    @NotNull(message = "Valid Price Required")
    @Positive(message = "Valid Price Required")
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddToCartInDTO that = (AddToCartInDTO) o;
        return userId == that.userId && restaurantId == that.restaurantId && foodId == that.foodId && quantity == that.quantity && Double.compare(price, that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId, foodId, quantity, price);
    }
}
