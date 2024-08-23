package com.capstone.users_service.service;

import com.capstone.users_service.InDTO.LoginRequestDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseDTO;
import com.capstone.users_service.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * Save user to database.
     * @param userInDTO request object
     * @return message after saving user to database
     */
    ResponseEntity<String> save(UserInDTO userInDTO);

    /**
     * Check for user with a specific email and password.
     * @param loginRequestDTO login credentials
     * @return the user details if exists
     */
    ResponseEntity<LoginResponseDTO> loginUser(LoginRequestDTO loginRequestDTO);
}
