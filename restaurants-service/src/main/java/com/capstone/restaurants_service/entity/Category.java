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
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    /**
     * categoryId for pairing with the category_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;
    /**
     * restaurantId for pairing with the restaurant_id field in database using ORM.
     */
    @Column(name = "restaurant_id")
    private long restaurantId;
    /**
     * name for pairing with the name field in database using ORM.
     */
    @Column(name = "name")
    private String name;

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
        Category category = (Category) o;
        return categoryId == category.categoryId
                && restaurantId == category.restaurantId
                && Objects.equals(name, category.name);
    }

    /**
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(categoryId, restaurantId, name);
    }
}
