package com.capstone.restaurants_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Restaurant In DTO to add Restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInDTO {
    /**
     * restaurantId to map with restaurantId field of entity.
     */
    @NotNull(message = "Valid restaurantId required")
    @Min(value = 1, message = "Valid restaurantId required")
    private long restaurantId;
    /**
     * name to map with name field of entity.
     */
    @NotBlank(message = "Valid name required")
    private String name;
}
