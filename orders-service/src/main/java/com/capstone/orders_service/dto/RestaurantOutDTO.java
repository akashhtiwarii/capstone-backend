package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOutDTO {
    private long restaurantId;
    private long ownerId;
    private String name;
    private String email;
    private String phone;
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantOutDTO that = (RestaurantOutDTO) o;
        return restaurantId == that.restaurantId && ownerId == that.ownerId && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.deepEquals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, ownerId, name, email, phone, Arrays.hashCode(image));
    }
}
