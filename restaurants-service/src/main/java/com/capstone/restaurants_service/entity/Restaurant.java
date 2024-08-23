package com.capstone.restaurants_service.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    /**
     * restaurantId to connect with the restaurantId field in database using ORM.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private long restaurantId;

    /**
     * ownerId to connect with the owner_id field in database using ORM.
     */
    @Column(name = "owner_id")
    private long ownerId;

    /**
     * name to connect with the name field in database using ORM.
     */
    @Column(name = "name")
    private String name;

    /**
     * email to connect with the email field in database using ORM.
     */
    @Column(name = "email")
    private String email;

    /**
     * phone to connect with the phone field in database using ORM.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * address to connect with the address field in database using ORM.
     */
    @Column(name = "address")
    private String address;
}
