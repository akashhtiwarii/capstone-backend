package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data Transfer Object for representing address information.
 * This class is used to transfer address details between different layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutDTO {

    /**
     * The unique identifier for the address.
     */
    private long addressId;

    /**
     * The unique identifier for the user associated with the address.
     */
    private long userId;

    /**
     * The detailed address information (e.g., street address).
     */
    private String address;

    /**
     * The postal code or pin code of the address.
     */
    private String pincode;

    /**
     * The city in which the address is located.
     */
    private String city;

    /**
     * The state or region in which the address is located.
     */
    private String state;

    /**
     * Compares this AddressOutDTO object to the specified object for equality.
     * Two AddressOutDTO objects are considered equal if they have the same addressId, userId, address, pincode, city, and state.
     *
     * @param o the object to compare this AddressOutDTO against.
     * @return true if the specified object is equal to this AddressOutDTO; false otherwise.
     */
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

    /**
     * Returns a hash code value for this AddressOutDTO.
     * The hash code is generated based on addressId, userId, address, pincode, city, and state.
     *
     * @return a hash code value for this AddressOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, address, pincode, city, state);
    }

    /**
     * Returns a string representation of this AddressOutDTO.
     * The string representation includes userId, address, city, state, and pincode, separated by commas.
     *
     * @return a string representation of this AddressOutDTO.
     */
    @Override
    public String toString() {
        return (address + ", " + city + ", " + state + ", " + pincode);
    }
}
