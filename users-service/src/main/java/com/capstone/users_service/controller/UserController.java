package com.capstone.users_service.controller;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestDTO;
import com.capstone.users_service.InDTO.LoginRequestDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.AddressOutDTO;
import com.capstone.users_service.OutDTO.LoginResponseDTO;
import com.capstone.users_service.service.AddressService;
import com.capstone.users_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller for managing user-operations.
* */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
    * User Service for accessing user functions.
    */
    @Autowired
    private UserService userService;

    /**
     * Address Service for accessing address table operations.
     */
    @Autowired
    private AddressService addressService;

    /**
     * Registers a new user.
     * @param userInDTO the user to be registered
     * @return a ResponseEntity containing the registration status
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserInDTO userInDTO) {
        return userService.save(userInDTO);
    }

    /**
     * User Login.
     * @param loginRequestDTO login credentials
     * @return user details if exists
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.loginUser(loginRequestDTO);
    }

    /**
     * Get Addresses of a user.
     * @param addressRequestDTO Request body
     * @return address with a specific Id
     */
    @PostMapping("/address")
    public AddressOutDTO getAddressesById(@RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.findUserAddresses(addressRequestDTO);
    }

    /**
     * Add new address of a user.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @PostMapping("/address/add")
    public ResponseEntity<String> addAddress(@Valid @RequestBody AddressInDTO addressInDTO) {
        return addressService.addAddress(addressInDTO);
    }
}
