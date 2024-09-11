package com.capstone.orders_service.dto;

import lombok.Data;

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
}
