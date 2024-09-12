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
 * Represents a user entity mapped to the 'users' table in the database.
 * <p>
 * This class is used to persist and retrieve user information, including details such as user ID, name, email,
 * password, phone number, and role.
 * </p>
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user entity.
     * This field is mapped to the 'user_id' column in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    /**
     * The name of the user.
     * This field is mapped to the 'name' column in the database.
     */
    @Column(name = "name")
    private String name;

    /**
     * The email address of the user.
     * This field is mapped to the 'email' column in the database.
     */
    @Column(name = "email")
    private String email;

    /**
     * The password of the user.
     * This field is mapped to the 'password' column in the database.
     */
    @Column(name = "password")
    private String password;

    /**
     * The phone number of the user.
     * This field is mapped to the 'phone' column in the database.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * The role assigned to the user.
     * This field is mapped to the 'role' column in the database and uses an enumerated type for role values.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Compares this user entity with another object for equality.
     * <p>
     * Two user entities are considered equal if they have the same user ID, name, email, password, phone number,
     * and role.
     * </p>
     * @param o the object to be compared
     * @return true if this user is equal to the specified object, false otherwise
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
                && Objects.equals(phone, user.phone) && role == user.role;
    }

    /**
     * Returns a hash code value for this user entity.
     * <p>
     * The hash code is computed based on the user ID, name, email, password, phone number, and role.
     * </p>
     * @return a hash code value for this user entity
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email, password, phone, role);
    }
}
