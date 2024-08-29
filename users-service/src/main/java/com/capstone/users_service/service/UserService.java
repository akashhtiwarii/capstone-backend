package com.capstone.users_service.service;

import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.RestaurantInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import org.springframework.http.ResponseEntity;

/**
 * UserService for defining methods related to user table.
 */
public interface UserService {
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

    /**
     * Add a new restaurant.
     * @param restaurantInDTO
     * @return response entity object
     */
    ResponseEntity<String> addRestaurant(RestaurantInDTO restaurantInDTO);
}
