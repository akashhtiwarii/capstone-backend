package com.capstone.users_service.controllerTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.controller.UserController;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.service.AddressService;
import com.capstone.users_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserInDTO userInDTO = new UserInDTO("Akash Tiwari", "#Abcdefg1#", "akash@example.com", "9876543210", Role.USER);
        when(userService.save(any(UserInDTO.class))).thenReturn("User registered successfully");
        ResponseEntity<String> response = userController.registerUser(userInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).save(any(UserInDTO.class));
    }

    @Test
    void testRegisterUser_Exception() {
        UserInDTO userInDTO = new UserInDTO("Akash Tiwari", "#Abcdefg1#", "akash@example.com", "9876543210", Role.USER);
        when(userService.save(any(UserInDTO.class))).thenThrow(new RuntimeException("Registration failed"));
        try {
            userController.registerUser(userInDTO);
        } catch (RuntimeException e) {
            assertEquals("Registration failed", e.getMessage());
            verify(userService, times(1)).save(any(UserInDTO.class));
        }
    }

    @Test
    void testLoginUser_Success() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO("akash@example.com", "#Abcdefg1#");
        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO(1L, "akash@example.com", "Akash Tiwari", "9876543210", Role.USER, "Login successful");
        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponseOutDTO);
        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequestInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponseOutDTO, response.getBody());
        verify(userService, times(1)).loginUser(any(LoginRequestInDTO.class));
    }

    @Test
    void testLoginUser_Unauthorized() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO("akash@example.com", "wrongPassword");
        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO(0L, null, null, null, null, "Invalid credentials");
        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponseOutDTO);
        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequestInDTO);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(loginResponseOutDTO, response.getBody());
        verify(userService, times(1)).loginUser(any(LoginRequestInDTO.class));
    }
}