package com.capstone.users_service.convertersTest;

import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.converters.UserConverters;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.Enum.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserConvertersTest {

    @Test
    void testRegisterUserInDTOToUserEntity() {
        UserInDTO userInDTO = new UserInDTO(
                "Akash Tiwari",
                "Password123!",
                "akash@gmail.com",
                "1234567890",
                Role.USER
        );
        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);
        assertNotNull(user);
        assertEquals(userInDTO.getName(), user.getName());
        assertEquals(userInDTO.getPassword(), user.getPassword());
        assertEquals(userInDTO.getEmail(), user.getEmail());
        assertEquals(userInDTO.getRole(), user.getRole());
        assertEquals(userInDTO.getPhone(), user.getPhone());
    }

    @Test
    void testUserEntityToLoginResponseOutDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setName("Akash Tiwari");
        user.setEmail("akash@gmail.com");
        user.setPhone("1234567890");
        user.setRole(Role.USER);
        LoginResponseOutDTO loginResponse = UserConverters.userEntityToLoginResponseOutDTO(user);
        assertNotNull(loginResponse);
        assertEquals(user.getUserId(), loginResponse.getUserId());
        assertEquals(user.getEmail(), loginResponse.getEmail());
        assertEquals(user.getName(), loginResponse.getName());
        assertEquals(user.getPhone(), loginResponse.getPhone());
        assertEquals(user.getRole(), loginResponse.getRole());
        assertEquals("Login Successful", loginResponse.getMessage());
    }
}
