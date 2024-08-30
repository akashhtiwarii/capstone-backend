package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO To Update Food Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodItemInDTO {

    /**
     * The ID of the user performing the update operation.
     */
    @NotNull(message = "Valid User ID required")
    @Min(value = 1, message = "Valid User ID required")
    private long userId;

    /**
     * The ID of the category to which the food item belongs.
     */
    @NotNull(message = "Valid Category ID required")
    @Min(value = 1, message = "Valid Category ID required")
    private long categoryId;

    /**
     * The name of the food item.
     */
    @NotBlank(message = "Valid Name Required")
    private String name;

    /**
     * The description of the food item.
     */
    private String description;

    /**
     * The price of the food item.
     */
    @NotNull(message = "Valid Price required")
    @Positive(message = "Valid Price required")
    private double price;
}
