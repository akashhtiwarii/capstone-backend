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
 * Represents a category entity mapped to the 'categories' table in the database.
 * <p>
 * This class is used to persist and retrieve category information, including details such as category ID,
 * restaurant ID, and the name of the category.
 * </p>
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    /**
     * Unique identifier for the category entity.
     * This field is mapped to the 'category_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;

    /**
     * Identifier for the restaurant associated with this category.
     * This field is mapped to the 'restaurant_id' column in the database.
     */
    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * The name of the category.
     * This field is mapped to the 'name' column in the database.
     */
    @Column(name = "name")
    private String name;

    /**
     * Compares this category entity with another object for equality.
     * <p>
     * Two category entities are considered equal if they have the same category ID, restaurant ID, and name.
     * </p>
     * @param o the object to be compared
     * @return true if this category is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
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
     * Returns a hash code value for this category entity.
     * <p>
     * The hash code is computed based on the category ID, restaurant ID, and name.
     * </p>
     * @return a hash code value for this category entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(categoryId, restaurantId, name);
    }
}
