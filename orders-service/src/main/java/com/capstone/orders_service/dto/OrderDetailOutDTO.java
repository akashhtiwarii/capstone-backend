package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for representing details of a food item in an order.
 * This class is used to transfer food item details from the server to the client within the context of an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailOutDTO {

    /**
     * The name of the food item included in the order.
     */
    private String foodName;

    /**
     * The quantity of the food item ordered.
     */
    private int quantity;

    /**
     * The price of a single unit of the food item.
     */
    private double price;
}
