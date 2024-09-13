package com.capstone.users_service.service;

import com.capstone.users_service.dto.ContactUsInDTO;
import com.capstone.users_service.dto.GetUserInfoInDTO;
import com.capstone.users_service.dto.LoginRequestInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.dto.ProfileOutDTO;
import com.capstone.users_service.dto.UpdateProfileInDTO;
import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.entity.User;

/**
 * Service interface for managing {@link User} entities.
 * Defines methods for performing CRUD operations and other user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a {@link User} by its unique identifier.
     *
     * @param getUserInfoInDTO the data transfer object containing the user ID to be retrieved
     * @return the {@link User} entity associated with the specified ID, or {@code null} if no such entity exists
     */
    User getById(GetUserInfoInDTO getUserInfoInDTO);

    /**
     * Retrieves the profile information of a user by their ID.
     *
     * @param userId the ID of the user whose profile information is to be retrieved
     * @return a {@link ProfileOutDTO} containing the user's profile information
     */
    ProfileOutDTO getProfileInfo(long userId);

    /**
     * Retrieves a {@link User} by its unique identifier for use with Feign clients.
     *
     * @param userId the ID of the user to be retrieved
     * @return the {@link User} entity associated with the specified ID
     */
    User getByIdentity(long userId);

    /**
     * Registers a new user in the system.
     *
     * @param userInDTO the data transfer object containing the user details to be saved
     * @return a {@link String} message indicating the result of the registration operation
     */
    String registerUser(UserInDTO userInDTO);

    /**
     * Authenticates a user and retrieves their details based on email and password.
     *
     * @param loginRequestInDTO the data transfer object containing login credentials
     * @return a {@link LoginResponseOutDTO} containing the user details if authentication is successful
     */
    LoginResponseOutDTO loginUser(LoginRequestInDTO loginRequestInDTO);

    /**
     * Updates the profile information of an existing user.
     *
     * @param userId the ID of the user whose profile is to be updated
     * @param updateProfileInDTO the data transfer object containing the updated profile details
     * @return a {@link String} message indicating the result of the profile update operation
     */
    String updateUserProfile(long userId, UpdateProfileInDTO updateProfileInDTO);

    /**
     * Handles user contact requests.
     *
     * @param contactUsInDTO the data transfer object containing contact request details
     * @return a {@link String} message indicating the result of the contact request operation
     */
    String contactUs(ContactUsInDTO contactUsInDTO);
}
