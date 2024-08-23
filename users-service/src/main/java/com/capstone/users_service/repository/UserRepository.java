package com.capstone.users_service.repository;

import com.capstone.users_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Get user by Id.
     * @param userId of user
     * @return User
     */
    User findById(long userId);

    /**
     * Get user by email.
     * @param email of user
     * @return User
     */
    User findByEmail(String email);

    /**
     * Check if the email already exists.
     * @param email of user
     * @return true if email exists false if it does not
     */
    boolean existsByEmail(String email);
    /**
     * Check for user with a specific email and password.
     * @param email of the user
     * @param password of the user
     * @return the user if exists
     */
    User findByEmailAndPassword(String email, String password);
}
