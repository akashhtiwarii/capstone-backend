package com.capstone.restaurants_service.converters;

import com.capstone.restaurants_service.dto.FoodItemInDTO;
import com.capstone.restaurants_service.entity.FoodItem;
import com.capstone.restaurants_service.utils.StringUtils;

import java.util.Objects;

/**
 * Utility class for converting between {@link FoodItemInDTO} and {@link FoodItem} entities.
 * This class contains methods to transform {@link FoodItemInDTO} objects into {@link FoodItem} entities.
 */
public class FoodItemConverters {

    /**
     * Converts a {@link FoodItemInDTO} object to a {@link FoodItem} entity.
     * <p>
     * This method performs the following transformations:
     * <ul>
     *     <li>Sets the category ID from the DTO to the entity.</li>
     *     <li>Capitalizes the first letter of the food item name using
     *     {@link StringUtils#capitalizeFirstLetter(String)}.</li>
     *     <li>Sets the description to an empty string if it is {@code null} or empty in the DTO.</li>
     *     <li>Sets the price from the DTO to the entity.</li>
     * </ul>
     * </p>
     *
     * @param foodItemInDTO the DTO containing food item data to be converted
     * @return a {@link FoodItem} entity populated with data from the provided DTO
     */
    public static FoodItem foodItemInDTOToFoodItemEntity(FoodItemInDTO foodItemInDTO) {
        FoodItem foodItem = new FoodItem();
        foodItem.setCategoryId(foodItemInDTO.getCategoryId());
        foodItem.setName(StringUtils.capitalizeFirstLetter(foodItemInDTO.getName()));
        foodItem.setDescription(
                foodItemInDTO.getDescription() == null || Objects.equals(foodItemInDTO.getDescription(), "")
                        ? "" : foodItemInDTO.getDescription());
        foodItem.setPrice(foodItemInDTO.getPrice());
        return foodItem;
    }
}
