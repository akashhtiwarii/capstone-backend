package com.capstone.users_service.controller;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.service.AddressService;
import com.capstone.users_service.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

import static com.capstone.users_service.utils.Constants.USER_ADDRESS_ENDPOINT;
import static com.capstone.users_service.utils.Constants.USER_ADD_ADDRESS_ENDPOINT;
import static com.capstone.users_service.utils.Constants.USER_ENDPOINT;
import static com.capstone.users_service.utils.Constants.USER_LOGIN_ENDPOINT;
import static com.capstone.users_service.utils.Constants.USER_REGISTER_ENDPOINT;

/**
 * Rest Controller for managing user-operations.
* */
@RestController
@RequestMapping(USER_ENDPOINT)
@CrossOrigin
public class UserController {

    /**
     * Logger for logging.
     */
    private final Logger logger = LogManager.getLogger(UserController.class);

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
    @PostMapping(USER_REGISTER_ENDPOINT)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserInDTO userInDTO) {
        logger.info("Registering new user: {}", userInDTO.getEmail());
        try {
            String message = userService.save(userInDTO);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(message);
            logger.info("User registered successfully: {}", userInDTO.getEmail());
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while registering user: {}", userInDTO.getEmail(), e);
            throw e;
        }
    }

    /**
     * User Login.
     * @param loginRequestInDTO login credentials
     * @return user details if exists
     */
    @PostMapping(USER_LOGIN_ENDPOINT)
    public ResponseEntity<LoginResponseOutDTO> loginUser(@Valid @RequestBody LoginRequestInDTO loginRequestInDTO) {
        logger.info("User login attempt: {}", loginRequestInDTO.getEmail());
        try {
            LoginResponseOutDTO response = userService.loginUser(loginRequestInDTO);
            if (response.getUserId() == 0) {
                logger.info("User Unauthorized: {}", response.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            } else {
                logger.info("User logged in successfully: {}", loginRequestInDTO.getEmail());
             return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            logger.error("Error occurred while logging in user: {}", loginRequestInDTO.getEmail(), e);
            throw e;
        }
    }

    /**
     * Get Addresses of a user.
     * @param addressRequestInDTO Request body
     * @return address with a specific Id
     */
    @PostMapping(USER_ADDRESS_ENDPOINT)
    public List<Address> getAddressesById(@RequestBody AddressRequestInDTO addressRequestInDTO) {
        logger.info("Fetching addresses for email ID: {}", addressRequestInDTO.getEmail());
        try {
            List<Address> addresses = addressService.findUserAddresses(addressRequestInDTO);
            logger.info("Addresses fetched successfully for email ID: {}", addressRequestInDTO.getEmail());
            return addresses;
        } catch (Exception e) {
            logger.error("Error occurred while fetching addresses for email ID: {}", addressRequestInDTO.getEmail(), e);
            throw e;
        }
    }

    /**
     * Add new address of a user.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @PostMapping(USER_ADD_ADDRESS_ENDPOINT)
    public ResponseEntity<String> addAddress(@Valid @RequestBody AddressInDTO addressInDTO) {
        logger.info("Adding new address for email ID: {}", addressInDTO.getEmail());
        try {
            String message = addressService.addAddress(addressInDTO);
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(message);
            logger.info("Address added successfully for email ID: {}", addressInDTO.getEmail());
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while adding address for email ID: {}", addressInDTO.getEmail(), e);
            throw e;
        }
    }
}
