package com.capstone.users_service.convertersTest;

import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.converters.UserConverters;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.Enum.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserConvertersTest {

    @Test
    public void testRegisterUserInDTOToUserEntity() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setName("John Doe ");
        userInDTO.setPassword("password123 ");
        userInDTO.setEmail("JohnDoe@example.com ");
        userInDTO.setRole(Role.USER);
        userInDTO.setPhone("1234567890 ");

        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);

        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());
        assertEquals("1234567890", user.getPhone());
    }

    @Test
    public void testUserEntityToLoginResponseOutDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("1234567890");
        user.setRole(Role.USER);

        LoginResponseOutDTO loginResponseOutDTO = UserConverters.userEntityToLoginResponseOutDTO(user);

        assertEquals(1L, loginResponseOutDTO.getUserId());
        assertEquals("johndoe@example.com", loginResponseOutDTO.getEmail());
        assertEquals("John Doe", loginResponseOutDTO.getName());
        assertEquals("1234567890", loginResponseOutDTO.getPhone());
        assertEquals(Role.USER, loginResponseOutDTO.getRole());
        assertEquals("Login Successful", loginResponseOutDTO.getMessage());
    }
}
