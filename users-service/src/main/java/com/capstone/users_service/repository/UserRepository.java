package com.capstone.users_service.repository;

import com.capstone.users_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing the {@link User} entity.
 * Provides methods to perform CRUD operations and custom queries
 * on the {@code user} table in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a {@link User} entity by its unique identifier.
     *
     * @param userId the ID of the user to be retrieved
     * @return the {@link User} entity with the specified ID, or {@code null} if no such entity exists
     */
    User findById(long userId);

    /**
     * Retrieves a {@link User} entity by its email address.
     *
     * @param email the email address of the user to be retrieved
     * @return the {@link User} entity with the specified email, or {@code null} if no such entity exists
     */
    User findByEmail(String email);

    /**
     * Checks if a {@link User} entity with the specified email address already exists.
     *
     * @param email the email address to be checked
     * @return {@code true} if a user with the specified email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves a {@link User} entity with the specified email address and password.
     *
     * @param email the email address of the user
     * @param password the password of the user
     * @return the {@link User} entity with the specified email and password, or {@code null} if no such entity exists
     */
    User findByEmailAndPassword(String email, String password);
}
