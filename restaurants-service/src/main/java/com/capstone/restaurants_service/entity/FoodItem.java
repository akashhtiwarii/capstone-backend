package com.capstone.restaurants_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * FoodItem Entity mapping with food_items table.
 */
@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    /**
     * foodId for pairing with the food_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private long foodId;
    /**
     * categoryId for pairing with the category_id field in database using ORM.
     */
    @Column(name = "category_id")
    private long categoryId;
    /**
     * name for pairing with the name field in database using ORM.
     */
    @Column(name = "name")
    private String name;
    /**
     * description for pairing with the description field in database using ORM.
     */
    @Column(name = "description")
    private String description;
    /**
     * price for pairing with the price field in database using ORM.
     */
    @Column(name = "price")
    private double price;

    /**
     * Override equals method for testing.
     * @param o object
     * @return true or false based on conditions
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodItem foodItem = (FoodItem) o;
        return foodId == foodItem.foodId
                && categoryId == foodItem.categoryId
                && Double.compare(price, foodItem.price) == 0
                && Objects.equals(name, foodItem.name)
                && Objects.equals(description, foodItem.description);
    }

    /**
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(foodId, categoryId, name, description, price);
    }
}
