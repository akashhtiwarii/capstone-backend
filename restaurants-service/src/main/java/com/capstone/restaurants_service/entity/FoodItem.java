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

@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    /**
     * foodId to connect with the food_id field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private long foodId;

    /**
     * categoryId to connect with the category_id field in database using ORM.
     */
    @Column(name = "category_id")
    private long categoryId;

    /**
     * name to connect with the name field in database using ORM.
     */
    @Column(name = "name")
    private String name;

    /**
     * description to connect with the description field in database using ORM.
     */
    @Column(name = "description")
    private String description;

    /**
     * price to connect with the price field in database using ORM.
     */
    @Column(name = "price")
    private double price;
}
