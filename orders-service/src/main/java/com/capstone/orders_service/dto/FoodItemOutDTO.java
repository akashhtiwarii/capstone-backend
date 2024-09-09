package com.capstone.orders_service.dto;

import lombok.Data;

@Data
public class FoodItemOutDTO {
    private long foodId;
    private long categoryId;
    private String name;
    private String description;
    private double price;
    private byte[] image;
}
