package com.capstone.users_service.controllerTests;

import com.capstone.users_service.dto.*;
import com.capstone.users_service.controller.UserController;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.service.AddressService;
import com.capstone.users_service.service.UserService;
import com.capstone.users_service.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUserReturnsSuccess() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("test@example.com");
        when(userService.registerUser(any(UserInDTO.class))).thenReturn("User registered successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.registerUser(userInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody().getMessage());
    }

    @Test
    public void loginUserReturnsSuccess() {
        LoginRequestInDTO loginRequest = new LoginRequestInDTO();
        loginRequest.setEmail("test@example.com");
        LoginResponseOutDTO loginResponse = new LoginResponseOutDTO();
        loginResponse.setUserId(1);
        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponse);
        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getUserId());
    }

    @Test
    public void loginUserReturnsUnauthorized() {
        LoginRequestInDTO loginRequest = new LoginRequestInDTO();
        loginRequest.setEmail("test@example.com");
        LoginResponseOutDTO loginResponse = new LoginResponseOutDTO();
        loginResponse.setUserId(0);
        loginResponse.setMessage("Unauthorized");
        when(userService.loginUser(any(LoginRequestInDTO.class))).thenReturn(loginResponse);
        ResponseEntity<LoginResponseOutDTO> response = userController.loginUser(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized", response.getBody().getMessage());
    }

    @Test
    public void getUserByIdReturnsUser() {
        GetUserInfoInDTO getUserInfoInDTO = new GetUserInfoInDTO();
        getUserInfoInDTO.setUserId(1);
        User user = new User();
        when(userService.getById(any(GetUserInfoInDTO.class))).thenReturn(user);
        ResponseEntity<User> response = userController.getUserById(getUserInfoInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getUserProfileReturnsProfile() {
        ProfileOutDTO profile = new ProfileOutDTO();
        profile.setName("John Doe");
        when(userService.getProfileInfo(anyLong())).thenReturn(profile);
        ResponseEntity<ProfileOutDTO> response = userController.getUserProfile(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void updateUserProfileReturnsSuccess() {
        UpdateProfileInDTO updateProfileInDTO = new UpdateProfileInDTO();
        when(userService.updateUserProfile(anyLong(), any(UpdateProfileInDTO.class))).thenReturn("Profile updated successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.updateUserProfile(1, updateProfileInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Profile updated successfully", response.getBody().getMessage());
    }

    @Test
    public void getUserByIdentityReturnsUser() {
        User user = new User();
        when(userService.getByIdentity(anyLong())).thenReturn(user);
        ResponseEntity<User> response = userController.getUserByIdentity(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void addAddressReturnsSuccess() {
        AddressInDTO addressInDTO = new AddressInDTO();
        when(addressService.addAddress(any(AddressInDTO.class))).thenReturn("Address added successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.addAddress(addressInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address added successfully", response.getBody().getMessage());
    }

    @Test
    public void getAddressByIdReturnsAddress() {
        Address address = new Address();
        when(addressService.getAddressById(anyLong())).thenReturn(address);
        ResponseEntity<Address> response = userController.getAddressById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteAddressReturnsSuccess() {
        when(addressService.deleteAddress(anyLong(), anyLong())).thenReturn("Address deleted successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.deleteAddress(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address deleted successfully", response.getBody().getMessage());
    }

    @Test
    public void updateAddressReturnsSuccess() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();
        when(addressService.updateAddress(any(UpdateAddressInDTO.class))).thenReturn("Address updated successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.updateAddress(updateAddressInDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address updated successfully", response.getBody().getMessage());
    }

    @Test
    public void findUserWalletReturnsWallet() {
        Wallet wallet = new Wallet();
        when(walletService.findByUserId(anyLong())).thenReturn(wallet);
        ResponseEntity<Wallet> response = userController.findUserWallet(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void updateUserWalletReturnsSuccess() {
        when(walletService.updateWallet(anyLong(), anyDouble())).thenReturn("Wallet updated successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.updateUserWallet(1, 100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wallet updated successfully", response.getBody().getMessage());
    }

    @Test
    public void rechargeWalletReturnsSuccess() {
        when(walletService.rechargeWallet(anyLong(), anyDouble())).thenReturn("Wallet recharged successfully");
        ResponseEntity<RequestSuccessOutDTO> response = userController.rechargeWallet(1, 100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wallet recharged successfully", response.getBody().getMessage());
    }

    @Test
    public void contactUsReturnsSuccess() {
        ContactUsInDTO contactUsInDTO = new ContactUsInDTO();
        when(userService.contactUs(any(ContactUsInDTO.class))).thenReturn("Message sent successfully");
        RequestSuccessOutDTO response = userController.contactUs(contactUsInDTO);
        assertEquals("Message sent successfully", response.getMessage());
    }
}