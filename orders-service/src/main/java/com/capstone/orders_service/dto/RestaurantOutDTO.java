package com.capstone.orders_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for representing a restaurant.
 * This class is used to transfer restaurant details from the server to the client.
 */
@Data
@NoArgsConstructor
public class RestaurantOutDTO {

    /**
     * The unique identifier for the restaurant.
     */
    private long restaurantId;

    /**
     * The unique identifier for the owner of the restaurant.
     */
    private long ownerId;

    /**
     * The name of the restaurant.
     */
    private String name;

    /**
     * The email address associated with the restaurant.
     */
    private String email;

    /**
     * The phone number associated with the restaurant.
     */
    private String phone;

    /**
     * The image of the restaurant in binary format.
     * This may be used to display the restaurant's image visually.
     */
    private byte[] image;

    /**
     * Constructs a new {@code RestaurantOutDTO} with the specified values.
     * <p>
     * This constructor initializes the fields of the {@code RestaurantOutDTO} with the provided values.
     * A new byte array is created from the given image array to ensure that the internal state is not affected by
     * external modifications.
     * </p>
     *
     * @param restaurantId the unique identifier for the restaurant; should not be negative
     * @param ownerId      the unique identifier for the owner of the restaurant; should not be negative
     * @param name         the name of the restaurant; may be {@code null}
     * @param email        the email address associated with the restaurant; may be {@code null}
     * @param phone        the phone number associated with the restaurant; may be {@code null}
     * @param image        the image of the restaurant in binary format; may be {@code null}
     */
    public RestaurantOutDTO(
            final long restaurantId,
            final long ownerId,
            final String name,
            final String email,
            final String phone,
            final byte[] image
    ) {
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image = image != null ? image.clone() : null;
    }

    /**
     * Sets the image of the restaurant.
     * <p>
     * This method takes a new image array and sets it to the internal image field, making a copy of it to ensure
     * the internal state cannot be modified externally.
     * </p>
     *
     * @param image the new image byte array; may be {@code null}
     */
    public void setImage(final byte[] image) {
        this.image = image != null ? image.clone() : null;
    }

    /**
     * Returns a copy of the image byte array of the restaurant.
     * <p>
     * This method returns a new byte array that contains the same data as the internal image array.
     * </p>
     *
     * @return a copy of the image byte array; may be {@code null}
     */
    public byte[] getImage() {
        return image != null ? image.clone() : null;
    }

    /**
     * Compares this RestaurantOutDTO object to the specified object for equality.
     * Two RestaurantOutDTO objects are considered equal
     * if they have the same restaurantId, ownerId, name, email, phone, and image.
     *
     * @param o the object to compare this RestaurantOutDTO against.
     * @return true if the specified object is equal to this RestaurantOutDTO; false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOutDTO that = (RestaurantOutDTO) o;
        return restaurantId == that.restaurantId
                && ownerId == that.ownerId
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone)
                && Objects.deepEquals(image, that.image);
    }

    /**
     * Returns a hash code value for this RestaurantOutDTO.
     * The hash code is generated based on restaurantId, ownerId, name, email, phone, and image.
     *
     * @return a hash code value for this RestaurantOutDTO.
     */
    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, ownerId, name, email, phone, Arrays.hashCode(image));
    }
}
