package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for representing a restaurant.
 * This class is used to transfer restaurant details from the server to the client.
 */
@Data
@AllArgsConstructor
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
     * Compares this RestaurantOutDTO object to the specified object for equality.
     * Two RestaurantOutDTO objects are considered equal
     * if they have the same restaurantId, ownerId, name, email, phone, and image.
     *
     * @param o the object to compare this RestaurantOutDTO against.
     * @return true if the specified object is equal to this RestaurantOutDTO; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
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
