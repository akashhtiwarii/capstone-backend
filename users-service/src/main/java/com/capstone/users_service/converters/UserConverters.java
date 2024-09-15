package com.capstone.users_service.converters;

import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;

/**
 * Utility class for converting between User data transfer objects (DTOs) and User entities.
 * Provides methods to convert {@link UserInDTO}
 * to {@link User} entity and {@link User} entity to {@link LoginResponseOutDTO}.
 */
public class UserConverters {

    /**
     * Converts a {@link UserInDTO} to a {@link User} entity.
     *
     * @param userInDTO The data transfer object containing user registration details.
     * @return A {@link User} entity with values populated from the provided {@link UserInDTO}.
     */
    public static User registerUserInDTOToUserEntity(UserInDTO userInDTO) {
        User user = new User();
        user.setName(userInDTO.getName().trim());
        user.setPassword(userInDTO.getPassword().trim());
        user.setEmail(userInDTO.getEmail().trim().toLowerCase());
        user.setRole(userInDTO.getRole());
        user.setPhone(userInDTO.getPhone().trim());
        return user;
    }

    /**
     * Converts a {@link User} entity to a {@link LoginResponseOutDTO}.
     *
     * @param user The {@link User} entity to be converted.
     * @return A {@link LoginResponseOutDTO} containing user details and a success message.
     */
    public static LoginResponseOutDTO userEntityToLoginResponseOutDTO(User user) {
        return new LoginResponseOutDTO(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getRole(),
                "Login Successful");
    }
}
