package com.capstone.users_service.controller;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.ContactUsInDTO;
import com.capstone.users_service.dto.GetUserInfoInDTO;
import com.capstone.users_service.dto.LoginRequestInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.dto.ProfileOutDTO;
import com.capstone.users_service.dto.RequestSuccessOutDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.dto.UpdateProfileInDTO;
import com.capstone.users_service.dto.UserInDTO;
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
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import com.capstone.users_service.utils.Constants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller for managing user-related operations.
 * This controller handles requests for user registration, login, profile management,
 * address management, and wallet operations. It also includes endpoints for user contact.
 */
@RestController
@RequestMapping(Constants.USER_ENDPOINT)
@CrossOrigin
public class UserController {

    /**
     * Logger for logging user operations.
     */
    private final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Service for performing operations related to users.
     */
    @Autowired
    private UserService userService;

    /**
     * Service for performing operations related to addresses.
     */
    @Autowired
    private AddressService addressService;

    /**
     * Service for performing operations related to wallets.
     */
    @Autowired
    private WalletService walletService;

    /**
     * Registers a new user.
     * @param userInDTO the user information to be registered
     * @return a ResponseEntity containing the registration status message
     */
    @PostMapping(Constants.USER_REGISTER_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> registerUser(@Valid @RequestBody UserInDTO userInDTO) {
        logger.info("Registering new user: {}", userInDTO.getEmail());
        String message = userService.registerUser(userInDTO);
        logger.info("User registered successfully: {}", userInDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Authenticates a user and returns login details.
     * @param loginRequestInDTO login credentials
     * @return a ResponseEntity containing user details or an unauthorized message
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
     * Retrieves user information by ID.
     * @param getUserInfoInDTO contains the user ID to fetch information
     * @return a ResponseEntity containing user information
     */
    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestBody GetUserInfoInDTO getUserInfoInDTO) {
        logger.info("Fetching user info..{}", getUserInfoInDTO.getUserId());
        User user = userService.getById(getUserInfoInDTO);
        logger.info("Fetched user info..{}", getUserInfoInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Retrieves user profile information.
     * @param userId the ID of the user whose profile is to be fetched
     * @return a ResponseEntity containing user profile information
     */
    @GetMapping("/profile")
    public ResponseEntity<ProfileOutDTO> getUserProfile(@RequestParam long userId) {
        logger.info("Fetching user info..{}", userId);
        ProfileOutDTO profileOutDTO = userService.getProfileInfo(userId);
        logger.info("Fetched user info..{}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(profileOutDTO);
    }

    /**
     * Updates user profile information.
     * @param userId the ID of the user whose profile is to be updated
     * @param updateProfileInDTO the new profile information
     * @return a ResponseEntity containing the update status message
     */
    @PutMapping("/profile/update")
    public ResponseEntity<RequestSuccessOutDTO> updateUserProfile(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @Valid @RequestBody UpdateProfileInDTO updateProfileInDTO
    ) {
        logger.info("Updating user info..{}", userId);
        String message = userService.updateUserProfile(userId, updateProfileInDTO);
        logger.info("Updated user info..{}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Retrieves user information by identity for Feign client.
     * @param userId the ID of the user to fetch information
     * @return a ResponseEntity containing user information
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByIdentity(@PathVariable("id") long userId) {
        logger.info("Fetching user info..{}", userId);
        User user = userService.getByIdentity(userId);
        logger.info("Fetched user info..{}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Adds a new address for a user.
     * @param addressInDTO the address information to be added
     * @return a ResponseEntity containing the add address status message
     */
    @PostMapping(Constants.USER_ADD_ADDRESS_ENDPOINT)
    public ResponseEntity<RequestSuccessOutDTO> addAddress(@Valid @RequestBody AddressInDTO addressInDTO) {
        logger.info("Adding new address for User ID: {}", addressInDTO.getUserId());
        String message = addressService.addAddress(addressInDTO);
        logger.info("Address added successfully for User ID: {}", addressInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Retrieves an address by its ID.
     * @param addressId the ID of the address to be fetched
     * @return a ResponseEntity containing the address information
     */
    @GetMapping("/address/id")
    public ResponseEntity<Address> getAddressById(@RequestParam long addressId) {
        logger.info("Fetching address for address ID: {}", addressId);
        Address response = addressService.getAddressById(addressId);
        logger.info("Fetched address for address ID: {}", addressId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves a list of addresses for a user by their user ID.
     * @param userId the ID of the user whose addresses are to be fetched
     * @return a ResponseEntity containing the list of addresses
     */
    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAddressByUserId(@RequestParam long userId) {
        logger.info("Fetching address for user ID: {}", userId);
        List<Address> response = addressService.getAddressByUserId(userId);
        logger.info("Fetched address for user ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Deletes an address for a user.
     * @param userId the ID of the user whose address is to be deleted
     * @param addressId the ID of the address to be deleted
     * @return a ResponseEntity containing the delete address status message
     */
    @DeleteMapping("/address/delete")
    public ResponseEntity<RequestSuccessOutDTO> deleteAddress(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @Min(value = 1, message = "Valid Address ID required") long addressId
    ) {
        logger.info("Deleting address for user ID: {}", userId);
        String response = addressService.deleteAddress(userId, addressId);
        logger.info("Deleted address for user ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Updates an address for a user.
     * @param updateAddressInDTO the address information to be updated
     * @return a ResponseEntity containing the update address status message
     */
    @PutMapping("address/update")
    public ResponseEntity<RequestSuccessOutDTO> updateAddress(
            @Valid @RequestBody UpdateAddressInDTO updateAddressInDTO
    ) {
        logger.info("Updating address for user ID: {}", updateAddressInDTO.getUserId());
        String response = addressService.updateAddress(updateAddressInDTO);
        logger.info("Updated address for user ID: {}", updateAddressInDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(response));
    }

    /**
     * Retrieves the wallet information for a user.
     * @param userId the ID of the user whose wallet is to be fetched
     * @return a ResponseEntity containing the wallet information
     */
    @GetMapping("/wallet")
    public ResponseEntity<Wallet> findUserWallet(@RequestParam long userId) {
        logger.info("Fetching wallet for userId: {}", userId);
        Wallet wallet = walletService.findByUserId(userId);
        logger.info("Fetched wallet for userId: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(wallet);
    }

    /**
     * Updates the wallet balance for a user.
     * @param userId the ID of the user whose wallet is to be updated
     * @param amount the amount to be added or subtracted from the wallet balance
     * @return a ResponseEntity containing the update wallet status message
     */
    @PutMapping("/wallet/update")
    public ResponseEntity<RequestSuccessOutDTO> updateUserWallet(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @PositiveOrZero(message = "Valid amount required") double amount
    ) {
        logger.info("Updating Wallet for UserID : {}", userId);
        String message = walletService.updateWallet(userId, amount);
        logger.info("Updated Wallet for UserID : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Recharges the wallet with a specified amount.
     * @param userId the ID of the user whose wallet is to be recharged
     * @param amount the amount to recharge into the wallet
     * @return a ResponseEntity containing the recharge wallet status message
     */
    @PutMapping("/wallet/recharge")
    public ResponseEntity<RequestSuccessOutDTO> rechargeWallet(
            @RequestParam @Min(value = 1, message = "Valid User ID required") long userId,
            @RequestParam @Positive(message = "Valid Amount required") double amount
    ) {
        logger.info("Recharging Wallet for UserID : {}", userId);
        String message = walletService.rechargeWallet(userId, amount);
        logger.info("Recharged Wallet for UserID : {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(new RequestSuccessOutDTO(message));
    }

    /**
     * Sends a contact us message to a specified email address.
     * @param contactUsDTO contains the details of the contact us request
     * @return a RequestSuccessOutDTO containing the status message
     */
    @PostMapping("/contact-us")
    public RequestSuccessOutDTO contactUs(@RequestBody ContactUsInDTO contactUsDTO) {
        logger.info("Sending Mail to restaurant : {}", contactUsDTO.getRestaurantEmail());
        String message = userService.contactUs(contactUsDTO);
        logger.info("Sent Mail to restaurant : {}", contactUsDTO.getRestaurantEmail());
        return new RequestSuccessOutDTO(message);
    }
}
