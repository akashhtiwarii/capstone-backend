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
 * Represents a food item entity mapped to the 'food_items' table in the database.
 * <p>
 * This class is used to persist and retrieve food item information, including details such as food ID, category ID,
 * name, description, price, and image of the food item.
 * </p>
 */
@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
public class FoodItem {

    /**
     * Unique identifier for the food item entity.
     * This field is mapped to the 'food_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private long foodId;

    /**
     * Identifier for the category to which this food item belongs.
     * This field is mapped to the 'category_id' column in the database.
     */
    @Column(name = "category_id")
    private long categoryId;

    /**
     * The name of the food item.
     * This field is mapped to the 'name' column in the database.
     */
    @Column(name = "name")
    private String name;

    /**
     * The description of the food item.
     * This field is mapped to the 'description' column in the database.
     */
    @Column(name = "description")
    private String description;

    /**
     * The price of the food item.
     * This field is mapped to the 'price' column in the database.
     */
    @Column(name = "price")
    private double price;

    /**
     * The image of the food item.
     * This field is mapped to the 'image' column in the database and is stored as a binary large object (BLOB).
     */
    @Lob
    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    /**
     * Constructs a new {@link FoodItem} with the specified attributes.
     * <p>
     * This constructor creates a defensive copy of the {@code image} array to prevent external modification of the
     * internal representation.
     * </p>
     *
     * @param foodIdValue the unique identifier for the food item
     * @param categoryIdValue the identifier for the category to which the food item belongs
     * @param nameValue the name of the food item
     * @param descriptionValue a brief description of the food item
     * @param priceValue the price of the food item
     * @param imageValue the image of the food item as a byte array, or {@code null} if no image is provided;
     *              a defensive copy of this array will be stored internally
     */
    public FoodItem(
            final long foodIdValue,
            final long categoryIdValue,
            final String nameValue,
            final String descriptionValue,
            final double priceValue,
            final byte[] imageValue) {
        this.foodId = foodIdValue;
        this.categoryId = categoryIdValue;
        this.name = nameValue;
        this.description = descriptionValue;
        this.price = priceValue;
        this.image = imageValue != null ? imageValue.clone() : null;
    }


    /**
     * Returns a defensive copy of the image array.
     * @return a copy of the image array or null if the image is null
     */
    public byte[] getImage() {
        return image != null ? image.clone() : null;
    }

    /**
     * Sets the image by storing a defensive copy of the input array.
     * @param imageValue the image array to set
     */
    public void setImage(final byte[] imageValue) {
        this.image = imageValue != null ? imageValue.clone() : null;
    }
    /**
     * Compares this food item entity with another object for equality.
     * <p>
     * Two food item entities are considered equal if they have the same food ID, category ID, name, description,
     * price, and image.
     * </p>
     * @param o the object to be compared
     * @return true if this food item is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodItem foodItem = (FoodItem) o;
        return foodId == foodItem.foodId
                && categoryId == foodItem.categoryId
                && Double.compare(price, foodItem.price) == 0
                && Objects.equals(name, foodItem.name)
                && Objects.equals(description, foodItem.description)
                && Objects.deepEquals(image, foodItem.image);
    }

    /**
     * Returns a hash code value for this food item entity.
     * <p>
     * The hash code is computed based on the food ID, category ID, name, description, price, and image.
     * </p>
     * @return a hash code value for this food item entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(foodId, categoryId, name, description, price, Arrays.hashCode(image));
    }
}
