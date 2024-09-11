package com.capstone.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailOutDTO {
    private String foodName;
    private int quantity;
    private double price;
}
