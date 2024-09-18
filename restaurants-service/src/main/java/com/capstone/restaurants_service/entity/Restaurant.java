package com.capstone.restaurants_service.entity;

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
 * Represents a restaurant entity mapped to the 'restaurants' table in the database.
 * <p>
 * This class is used to persist and retrieve restaurant information, including details such as restaurant ID,
 * owner ID, name, email, phone number, address, and image of the restaurant.
 * </p>
 */
@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant {

    /**
     * Unique identifier for the restaurant entity.
     * This field is mapped to the 'restaurant_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * Identifier for the owner of the restaurant.
     * This field is mapped to the 'owner_id' column in the database.
     */
    @Column(name = "owner_id")
    private long ownerId;

    /**
     * The name of the restaurant.
     * This field is mapped to the 'name' column in the database.
     */
    @Column(name = "name")
    private String name;

    /**
     * The email address of the restaurant.
     * This field is mapped to the 'email' column in the database.
     */
    @Column(name = "email")
    private String email;

    /**
     * The phone number of the restaurant.
     * This field is mapped to the 'phone' column in the database.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * The address of the restaurant.
     * This field is mapped to the 'address' column in the database.
     */
    @Column(name = "address")
    private String address;

    /**
     * The image of the restaurant.
     * This field is mapped to the 'image' column in the database and is stored as a binary large object (BLOB).
     */
    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    /**
     * Constructs a new {@code Restaurant} instance with the specified attributes.
     *
     * @param restaurantIdValue the unique identifier for the restaurant
     * @param ownerIdValue the identifier for the owner of the restaurant
     * @param nameValue the name of the restaurant
     * @param emailValue the email address of the restaurant
     * @param phoneValue the phone number of the restaurant
     * @param addressValue the address of the restaurant
     * @param imageValue the image of the restaurant as a byte array.
     * A copy of this array is stored to prevent external modifications.
     */
    public Restaurant(
            final long restaurantIdValue,
            final long ownerIdValue,
            final String nameValue,
            final String emailValue,
            final String phoneValue,
            final String addressValue,
            final byte[] imageValue
    ) {
        this.restaurantId = restaurantIdValue;
        this.ownerId = ownerIdValue;
        this.name = nameValue;
        this.email = emailValue;
        this.phone = phoneValue;
        this.address = addressValue;
        this.image = (imageValue != null) ? imageValue.clone() : null;
    }

    /**
     * Returns a copy of the image of the restaurant.
     *
     * <p>This method returns a copy of the internal byte array representing the restaurant's image.
     * This ensures that the internal state of the restaurant object cannot be modified by external code.</p>
     *
     * @return a byte array containing the image of the restaurant, or {@code null} if no image is set
     */
    public byte[] getImage() {
        return (image != null) ? image.clone() : null;
    }

    /**
     * Sets the image of the restaurant.
     *
     * <p>This method sets the restaurant's image to a new byte array. A copy of the provided byte array is made to
     * ensure that changes to the original array outside of this class
     * do not affect the internal state of the restaurant.</p>
     * @param imageValue a byte array containing the new image for the restaurant.
     * A copy of this array is stored to prevent external modifications.
     * If {@code null} is provided, the restaurant's image is cleared.
     */
    public void setImage(final byte[] imageValue) {
        this.image = (imageValue != null) ? imageValue.clone() : null;
    }


    /**
     * Compares this restaurant entity with another object for equality.
     * <p>
     * Two restaurant entities are considered equal if they have the same restaurant ID, owner ID, name, email,
     * phone number, address, and image.
     * </p>
     * @param o the object to be compared
     * @return true if this restaurant is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return restaurantId == that.restaurantId
                && ownerId == that.ownerId
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone)
                && Objects.equals(address, that.address)
                && Objects.deepEquals(image, that.image);
    }

    /**
     * Returns a hash code value for this restaurant entity.
     * <p>
     * The hash code is computed based on the restaurant ID, owner ID, name, email, phone number, address,
     * and image.
     * </p>
     * @return a hash code value for this restaurant entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, ownerId, name, email, phone, address, Arrays.hashCode(image));
    }
}
