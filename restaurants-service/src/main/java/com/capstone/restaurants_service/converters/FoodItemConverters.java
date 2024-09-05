package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.utils.StringUtils;

import java.util.Objects;

/**
 * FoodItem In DTOs to Entity Converters and Vice Versa.
 */
public class FoodItemConverters {

    /**
     * Convert Food Item InDTO to FoodItem Entity.
     * @param foodItemInDTO
     * @return Food Item
     */
    public static FoodItem foodItemInDTOToFoodItemEntity(FoodItemInDTO foodItemInDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(foodItemInDTO.getCategoryId());
        foodItem.setName(StringUtils.capitalizeFirstLetter(foodItemInDTO.getName()));
        foodItem.setDescription(
                foodItemInDTO.getDescription() == null || Objects.equals(foodItemInDTO.getDescription(),
                        "") ? "" : foodItemInDTO.getDescription());
        foodItem.setPrice(foodItemInDTO.getPrice());
        return foodItem;
    }
}
