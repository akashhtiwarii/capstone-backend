package com.capstone.users_service.controllerTests;

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
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("test@gmail.com");

        when(userService.registerUser(any(UserInDTO.class))).thenReturn("User registered successfully");

        ResponseEntity<String> response = userController.registerUser(userInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void testLoginUserSuccess() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("test@gmail.com");

        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();
        loginResponseOutDTO.setUserId(1);
        loginResponseOutDTO.setEmail("test@gmail.com");

        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponseOutDTO);

        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequestInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getUserId());
        assertEquals("test@gmail.com", response.getBody().getEmail());
    }

    @Test
    void testLoginUserUnauthorized() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("unauthorized@gmail.com");

        LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();
        loginResponseOutDTO.setUserId(0);
        loginResponseOutDTO.setMessage("Unauthorized");

        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponseOutDTO);

        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequestInDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized", response.getBody().getMessage());
    }

    @Test
    void testGetAddressesById() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO();
        addressRequestInDTO.setEmail("test@gmail.com");

        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());

        when(addressService.findUserAddresses(any(AddressRequestInDTO.class))).thenReturn(addresses);

        List<Address> response = userController.getAddressesById(addressRequestInDTO);

        assertEquals(1, response.size());
    }

    @Test
    void testAddAddressSuccess() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("test@gmail.com");

        when(addressService.addAddress(any(AddressInDTO.class))).thenReturn("Address added successfully");

        ResponseEntity<String> response = userController.addAddress(addressInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address added successfully", response.getBody());
    }
}