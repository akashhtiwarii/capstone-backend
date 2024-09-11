package com.capstone.orders_service.dto;

import lombok.Data;

/**
 * Data Transfer Object for representing a food item associated with a user.
 * This class is used to transfer food item details from the server to the client.
 */
@Data
public class UserFoodItemOutDTO {

    /**
     * The unique identifier for the food item.
     */
    private long foodId;

    /**
     * The name of the food item.
     */
    private String name;

    /**
     * The quantity of the food item.
     */
    private int quantity;

    /**
     * The price of the food item.
     */
    private double price;
}
