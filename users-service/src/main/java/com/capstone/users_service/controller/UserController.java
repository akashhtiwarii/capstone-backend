package com.capstone.users_service.controller;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.InDTO.GetUserInfoInDTO;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.service.AddressService;
import com.capstone.users_service.service.UserService;
import com.capstone.users_service.service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;

import com.capstone.users_service.utils.Constants;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for managing user-operations.
* */
@RestController
@RequestMapping(Constants.USER_ENDPOINT)
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

    @Autowired
    private WalletService walletService;

    /**
     * Registers a new user.
     * @param userInDTO the user to be registered
     * @return a ResponseEntity containing the registration status
     */
    @PostMapping(Constants.USER_REGISTER_ENDPOINT)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserInDTO userInDTO) {
        logger.info("Registering new user: {}", userInDTO.getEmail());
        String message = userService.registerUser(userInDTO);
        logger.info("User registered successfully: {}", userInDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * User Login.
     * @param loginRequestInDTO login credentials
     * @return user details if exists
     */
    @PostMapping(Constants.USER_LOGIN_ENDPOINT)
    public ResponseEntity<LoginResponseOutDTO> loginUser(@Valid @RequestBody LoginRequestInDTO loginRequestInDTO) {
        logger.info("User login attempt: {}", loginRequestInDTO.getEmail());
        LoginResponseOutDTO response = userService.loginUser(loginRequestInDTO);
        if (response.getUserId() == 0) {
            logger.info("User Unauthorized: {}", response.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            logger.info("User logged in successfully: {}", loginRequestInDTO.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    /**
     * Get User by Id.
     * @param getUserInfoInDTO
     * @return user
     */
    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestBody GetUserInfoInDTO getUserInfoInDTO) {
        logger.info("Fetching user info..{}", getUserInfoInDTO.getUserId());
        User user = userService.getById(getUserInfoInDTO);
        logger.info("Fetched user info..{}", getUserInfoInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Get by ID for feign client.
     * @param userId
     * @return user
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByIdentity(@PathVariable("id") long userId) {
        logger.info("Fetching user info..{}", userId);
        User user = userService.getByIdentity(userId);
        logger.info("Fetched user info..{}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    /**
     * Add new address of a user.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @PostMapping(Constants.USER_ADD_ADDRESS_ENDPOINT)
    public ResponseEntity<String> addAddress(@Valid @RequestBody AddressInDTO addressInDTO) {
        logger.info("Adding new address for email ID: {}", addressInDTO.getEmail());
        String message = addressService.addAddress(addressInDTO);
        logger.info("Address added successfully for email ID: {}", addressInDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping("/address")
    public ResponseEntity<Address> getAddressById(@RequestParam long userId) {
        logger.info("Fetching address for user ID: {}", userId);
        Address response = addressService.getAddressById(userId);
        logger.info("Fetched address for user ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/wallet")
    public ResponseEntity<Wallet> findUserWallet(@RequestParam long userId) {
        logger.info("Fetching wallet for userId: {}", userId);
        Wallet wallet = walletService.findByUserId(userId);
        logger.info("Fetched wallet for userId: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(wallet);
    }

    @PutMapping("/wallet/update")
    public ResponseEntity<String> updateUserWallet(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @PositiveOrZero(message = "Valid amount required") double amount
    ) {
        logger.info("Updating Wallet for UserID : {}", userId);
        String message = walletService.updateWallet(userId, amount);
        logger.info("Updated Wallet for UserID : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
