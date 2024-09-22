package com.capstone.orders_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for representing a food item.
 * <p>
 * This class is used to encapsulate and transfer food item details between the server and client. It includes
 * properties related to the food item's identifier, category, name, description, price, and image.
 * </p>
 */
@Data
@NoArgsConstructor
public class FoodItemOutDTO {

    /**
     * The unique identifier for the food item.
     * <p>
     * This ID is used to uniquely identify the food item in the system.
     * </p>
     */
    private long foodId;

    /**
     * The unique identifier for the category to which the food item belongs.
     * <p>
     * This ID links the food item to a specific category, providing context about the type of food.
     * </p>
     */
    private long categoryId;

    /**
     * The name of the food item.
     * <p>
     * This is the name given to the food item, which is typically used for display purposes.
     * </p>
     */
    private String name;

    /**
     * A description of the food item.
     * <p>
     * This field provides additional details about the food item, such as ingredients or special features.
     * </p>
     */
    private String description;

    /**
     * The price of the food item.
     * <p>
     * This represents the cost of the food item, typically displayed to the user or customer.
     * </p>
     */
    private double price;

    /**
     * The image of the food item in binary format.
     * <p>
     * This byte array represents the visual image of the food item, which may be used for displaying pictures
     * </p>
     */
    private byte[] image;

    /**
     * Constructs a new {@code FoodItemOutDTO} with the specified values.
     * <p>
     * This constructor initializes the fields of the {@code FoodItemOutDTO} with the provided values.
     * A new array is created from the given image array to ensure that the internal state is not affected by
     * external modifications.
     * </p>
     *
     * @param foodId      the unique identifier for the food item
     * @param categoryId  the unique identifier for the category
     * @param name        the name of the food item
     * @param description a description of the food item
     * @param price       the price of the food item
     * @param image       the image of the food item; may be {@code null}
     */
    public FoodItemOutDTO(
            final long foodId,
            final long categoryId,
            final String name,
            final String description,
            final double price,
            final byte[] image
    ) {
        this.foodId = foodId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image != null ? image.clone() : null;
    }

    /**
     * Sets the image of the food item.
     * <p>
     * This method sets the image to a new array created from the provided array to protect the internal
     * state from external modifications.
     * </p>
     *
     * @param image the new image of the food item; may be {@code null}
     */
    public void setImage(final byte[] image) {
        this.image = image != null ? image.clone() : null;
    }

    /**
     * Returns a copy of the image byte array of the food item.
     * <p>
     * This method returns a new byte array that contains the same data as the internal image array.
     * </p>
     *
     * @return a copy of the image byte array
     */
    public byte[] getImage() {
        return image != null ? image.clone() : null;
    }

    /**
     * Compares this {@code FoodItemOutDTO} instance with another object for equality.
     * <p>
     * Two {@code FoodItemOutDTO} instances are considered equal if they have the same food ID, category ID,
     * name, description, price, and image content.
     * </p>
     *
     * @param o the object to be compared for equality with this instance
     * @return {@code true} if this instance is equal to the specified object; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodItemOutDTO that = (FoodItemOutDTO) o;
        return foodId == that.foodId
                && categoryId == that.categoryId
                && Double.compare(price, that.price) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.deepEquals(image, that.image);
    }

    /**
     * Returns a hash code value for this {@code FoodItemOutDTO} instance.
     * <p>
     * The hash code is computed based on the food ID, category ID, name, description, price, and image
     * content to support hash-based collections.
     * </p>
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(foodId, categoryId, name, description, price, Arrays.hashCode(image));
    }
}
