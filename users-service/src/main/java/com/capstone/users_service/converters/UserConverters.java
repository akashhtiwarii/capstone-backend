package com.capstone.users_service.converters;

import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;

/**
 * User In DTOs to Entity Converters and Vice Versa.
 */
public class UserConverters {
    /**
     * Convert UserInDTO To User Entity.
     * @param userInDTO Registration Request Body
     * @return User Entity
     */
    public static User registerUserInDTOToUserEntity(UserInDTO userInDTO) {
        User user = new User();
        user.setName(userInDTO.getName());
        user.setPassword(userInDTO.getPassword());
        user.setEmail(userInDTO.getEmail());
        user.setRole(userInDTO.getRole());
        user.setPhone(userInDTO.getPhone());
        return user;
    }

    /**
     * User Entity to Login Response DTO.
     * @param user entity
     * @return Login Response DTO
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
