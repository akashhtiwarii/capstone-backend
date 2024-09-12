package com.capstone.users_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Represents an address entity mapped to the 'address' table in the database.
 * <p>
 * This class is used to persist and retrieve address information including details such as address ID, user ID,
 * address line, pincode, city, and state.
 * </p>
 */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /**
     * Unique identifier for the address entity.
     * This field is mapped to the 'address_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    /**
     * Identifier for the user associated with this address.
     * This field is mapped to the 'user_id' column in the database.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * The address line.
     * This field is mapped to the 'address' column in the database.
     */
    @Column(name = "address")
    private String address;

    /**
     * The pincode of the address.
     * This field is mapped to the 'pincode' column in the database.
     */
    @Column(name = "pincode")
    private long pincode;

    /**
     * The city of the address.
     * This field is mapped to the 'city' column in the database.
     */
    @Column(name = "city")
    private String city;

    /**
     * The state of the address.
     * This field is mapped to the 'state' column in the database.
     */
    @Column(name = "state")
    private String state;

    /**
     * Compares this address entity with another object for equality.
     * <p>
     * Two address entities are considered equal if they have the same address ID, user ID, pincode, address line,
     * city, and state.
     * </p>
     * @param o the object to be compared
     * @return true if this address is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address1 = (Address) o;
        return addressId == address1.addressId && userId == address1.userId
                && pincode == address1.pincode && Objects.equals(address, address1.address)
                && Objects.equals(city, address1.city) && Objects.equals(state, address1.state);
    }

    /**
     * Returns a hash code value for this address entity.
     * <p>
     * The hash code is computed based on the address ID, user ID, pincode, address line, city, and state.
     * </p>
     * @return a hash code value for this address entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, address, pincode, city, state);
    }

    /**
     * Returns a string representation of this address entity.
     * <p>
     * The string representation includes the address line, city, state, and pincode.
     * </p>
     * @return a string representation of this address entity
     */
    @Override
    public String toString() {
        return (address + ", " + city + ", " + state + ", " + pincode);
    }
}
