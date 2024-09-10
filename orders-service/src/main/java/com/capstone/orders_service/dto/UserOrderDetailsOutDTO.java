package com.capstone.orders_service.dto;

import com.capstone.orders_service.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDetailsOutDTO {
    private String restaurantName;
    private List<UserFoodItemOutDTO> foodItemOutDTOS;
    private Status status;
}
