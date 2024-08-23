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

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /**
     * addressId for pairing with the address_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    /**
     * userId for pairing with the user_id field in database using ORM.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * address for pairing with the address field in database using ORM.
     */
    @Column(name = "address")
    private String address;

    /**
     * pincode for pairing with the pincode field in database using ORM.
     */
    @Column(name = "pincode")
    private long pincode;

    /**
     * city for pairing with the city field in database using ORM.
     */
    @Column(name = "city")
    private String city;

    /**
     * addressId for pairing with the address_id field in database using ORM.
     */
    @Column(name = "state")
    private String state;

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
        Address address1 = (Address) o;
        return addressId == address1.addressId && userId == address1.userId
                && pincode == address1.pincode && Objects.equals(address, address1.address)
                && Objects.equals(city, address1.city) && Objects.equals(state, address1.state);
    }

    /**
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(addressId, userId, address, pincode, city, state);
    }
}
