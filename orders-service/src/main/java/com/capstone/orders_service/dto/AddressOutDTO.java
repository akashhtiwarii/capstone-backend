package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutDTO {
    private long addressId;
    private long userId;
    private String address;
    private String pincode;
    private String city;
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressOutDTO that = (AddressOutDTO) o;
        return addressId == that.addressId && userId == that.userId && Objects.equals(address, that.address) && Objects.equals(pincode, that.pincode) && Objects.equals(city, that.city) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, address, pincode, city, state);
    }

    @Override
    public String toString() {
        return (userId + ", " + address + ", " + city + ", " + state + ", " + pincode);
    }
}
