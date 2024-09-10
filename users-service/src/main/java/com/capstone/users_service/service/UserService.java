package com.capstone.users_service.service;

import com.capstone.users_service.dto.*;
import com.capstone.users_service.entity.User;

/**
 * UserService for defining methods related to user table.
 */
public interface UserService {
    /**
     * Find user by ID.
     * @param getUserInfoInDTO
     * @return user
     */
    User getById(GetUserInfoInDTO getUserInfoInDTO);

    ProfileOutDTO getProfileInfo(long userId);

    /**
     * Get user for feign client.
     * @param userId
     * @return user
     */
    User getByIdentity(long userId);
    /**
     * Save user to database.
     * @param userInDTO request object
     * @return message after saving user to database
     */
    String registerUser(UserInDTO userInDTO);

    /**
     * Check for user with a specific email and password.
     * @param loginRequestInDTO login credentials
     * @return the user details if exists
     */
    LoginResponseOutDTO loginUser(LoginRequestInDTO loginRequestInDTO);

    String updateUserProfile(long userId, UpdateProfileInDTO updateProfileInDTO);
}
