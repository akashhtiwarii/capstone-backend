package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Objects;

import static com.capstone.restaurants_service.utils.Constants.PHONE_NUMBER_LENGTH;

/**
 * Data Transfer Object (DTO) for adding a restaurant.
 * <p>
 * This class encapsulates the data required to create or update a restaurant record.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInDTO {

    /**
     * The unique identifier of the owner associated with the restaurant.
     * <p>
     * This field links the restaurant to its owner by referencing the owner's ID.
     * </p>
     */
    @NotNull(message = "Valid OwnerID required")
    @Min(value = 1, message = "Valid OwnerID required")
    private long ownerId;

    /**
     * The name of the restaurant.
     * <p>
     * This field specifies the name of the restaurant and cannot be blank.
     * </p>
     */
    @NotBlank(message = "Enter a valid name for restaurant")
    private String name;

    /**
     * The email address of the restaurant.
     * <p>
     * This field must be a valid email address in the format of `example@gmail.com`.
     * </p>
     */
    @Email(message = "Enter a valid email ID for restaurant")
    @NotBlank(message = "Enter a valid email ID for restaurant")
    @Pattern(
            regexp = "^[\\w.%+-]+@gmail\\.com$",
            message = "Enter a valid email ID for restaurant"
    )
    private String email;

    /**
     * The phone number of the restaurant.
     * <p>
     * This field must be a valid phone number starting with 9, 8, 7, or 6,
     * followed by the appropriate number of digits.
     * </p>
     */
    @NotBlank(message = "Phone number should be valid")
    @Pattern(
            regexp = "^[9876]\\d{" + (PHONE_NUMBER_LENGTH - 1) + "}$",
            message = "Phone number should be valid"
    )
    private String phone;

    /**
     * The address of the restaurant.
     * <p>
     * This field specifies the restaurant's location and cannot be blank.
     * </p>
     */
    @NotBlank(message = "Address for restaurant cannot be empty")
    private String address;

    /**
     * Compares this RestaurantInDTO object with another object for equality.
     * <p>
     * Two RestaurantInDTO objects are considered equal if
     * their ownerId, name, email, phone, and address fields are equal.
     * </p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantInDTO that = (RestaurantInDTO) o;
        return ownerId == that.ownerId && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
                && Objects.equals(address, that.address);
    }

    /**
     * Returns a hash code value for this RestaurantInDTO object.
     * <p>
     * The hash code is computed based on the ownerId, name, email, phone, and address fields.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(ownerId, name, email, phone, address);
    }
}
