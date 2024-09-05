package com.capstone.restaurants_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

/**
 * Restaurant Entity mapping with restaurants table.
 */
@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    /**
     * restaurantId for pairing with the restaurant_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * ownerId for pairing with the owner_id field in database using ORM.
     */
    @Column(name = "owner_id")
    private long ownerId;

    /**
     * name for pairing with the name field in database using ORM.
     */
    @Column(name = "name")
    private String name;

    /**
     * email for pairing with the email field in database using ORM.
     */
    @Column(name = "email")
    private String email;

    /**
     * phone for pairing with the phone field in database using ORM.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * address for pairing with the address field in database using ORM.
     */
    @Column(name = "address")
    private String address;

    /**
     * image for pairing with the image field in database using ORM.
     */
    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

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
        Restaurant that = (Restaurant) o;
        return restaurantId == that.restaurantId && ownerId == that.ownerId && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
                && Objects.equals(address, that.address) && Objects.deepEquals(image, that.image);
    }

    /**
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, ownerId, name, email, phone, address, Arrays.hashCode(image));
    }
}
