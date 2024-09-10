package com.capstone.orders_service.dto;

import lombok.Data;

@Data
public class UserFoodItemOutDTO {
    private long foodId;
    private String name;
    private int quantity;
    private double price;
}
