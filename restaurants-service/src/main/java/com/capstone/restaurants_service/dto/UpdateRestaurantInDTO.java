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
 * Data Transfer Object (DTO) for updating an existing restaurant.
 * <p>
 * This DTO encapsulates the data required to update the details of a restaurant,
 * including the owner ID, restaurant ID, name, email, phone number, and address.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantInDTO {

    /**
     * The ID of the owner performing the update operation.
     * <p>
     * This field links the update action to the specific owner who is making the changes.
     * </p>
     */
    @NotNull(message = "Valid User ID Required")
    @Min(value = 1, message = "Valid User ID Required")
    private long loggedInOwnerId;

    /**
     * The ID of the restaurant to be updated.
     * <p>
     * This field specifies which restaurant is to be updated based on its unique identifier.
     * </p>
     */
    @NotNull(message = "Valid Restaurant ID Required")
    @Min(value = 1, message = "Valid Restaurant ID Required")
    private long restaurantId;

    /**
     * The name of the restaurant.
     * <p>
     * This field specifies the name of the restaurant and must not be blank.
     * </p>
     */
    @NotBlank(message = "Enter a valid name for restaurant")
    private String name;

    /**
     * The email address of the restaurant.
     * <p>
     * This field specifies the email address of the restaurant, which must be a valid Gmail address.
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
     * This field specifies the phone number of the restaurant and must adhere to the specified pattern.
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
     * This field specifies the address of the restaurant and must not be blank.
     * </p>
     */
    @NotBlank(message = "Address for restaurant cannot be empty")
    private String address;

    /**
     * Compares this UpdateRestaurantInDTO object with another object for equality.
     * <p>
     * Two UpdateRestaurantInDTO objects are considered equal if their loggedInOwnerId, restaurantId,
     * name, email, phone, and address fields are equal.
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
        UpdateRestaurantInDTO that = (UpdateRestaurantInDTO) o;
        return loggedInOwnerId == that.loggedInOwnerId
                && restaurantId == that.restaurantId
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phone, that.phone)
                && Objects.equals(address, that.address);
    }

    /**
     * Returns a hash code value for this UpdateRestaurantInDTO object.
     * <p>
     * The hash code is computed based on the fields: loggedInOwnerId, restaurantId, name, email, phone, and address.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(loggedInOwnerId, restaurantId, name, email, phone, address);
    }
}
