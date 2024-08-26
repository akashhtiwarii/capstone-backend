package com.capstone.users_service.entity;

import com.capstone.users_service.Enum.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * User Entity mapping with users table.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
    userId for connecting user_id field using ORM.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    /**
     name for connecting name field in database using ORM.
     */
    @Column(name = "name")
    private String name;

    /**
     email for connecting email field in database using ORM.
     */
    @Column(name = "email")
    private String email;

    /**
     password for connecting password field in database using ORM.
     */
    @Column(name = "password")
    private String password;

    /**
     phone for connecting phone field in database using ORM.
     */
    @Column(name = "phone")
    private String phone;

    /**
     address for connecting address field in database using ORM.
     */
    @Column(name = "address")
    private String address;

    /**
     role for connecting role field in database using ORM.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

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
        User user = (User) o;
        return userId == user.userId && Objects.equals(name, user.name)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && Objects.equals(phone, user.phone) && Objects.equals(address, user.address) && role == user.role;
    }

    /**
     * Override hashCode for testing.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email, password, phone, address, role);
    }
}
