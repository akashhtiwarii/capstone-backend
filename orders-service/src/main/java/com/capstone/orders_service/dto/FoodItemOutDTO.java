package com.capstone.orders_service.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for representing a food item.
 * This class is used to transfer food item details from the server to the client.
 */
@Data
public class FoodItemOutDTO {

    /**
     * The unique identifier for the food item.
     */
    private long foodId;

    /**
     * The unique identifier for the category to which the food item belongs.
     */
    private long categoryId;

    /**
     * The name of the food item.
     */
    private String name;

    /**
     * A description of the food item.
     */
    private String description;

    /**
     * The price of the food item.
     */
    private double price;

    /**
     * The image of the food item in binary format.
     * This may be used to display the food item visually.
     */
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodItemOutDTO that = (FoodItemOutDTO) o;
        return foodId == that.foodId && categoryId == that.categoryId && Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.deepEquals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId, categoryId, name, description, price, Arrays.hashCode(image));
    }
}
