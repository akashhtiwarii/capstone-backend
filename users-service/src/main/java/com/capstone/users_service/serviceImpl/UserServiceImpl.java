package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.dto.ContactUsInDTO;
import com.capstone.users_service.dto.GetUserInfoInDTO;
import com.capstone.users_service.dto.LoginRequestInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.dto.ProfileOutDTO;
import com.capstone.users_service.dto.UpdateProfileInDTO;
import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.exceptions.ResourceNotValidException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.service.UserService;
import com.capstone.users_service.utils.PasswordDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.capstone.users_service.converters.UserConverters;
import com.capstone.users_service.utils.Constants;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserService} for managing user-related operations.
 * This service handles user registration, profile updates, login,
 * and interactions related to user profiles and wallets.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * {@link JavaMailSender} for sending emails.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Repository for accessing the {@link User} table using JPA methods.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repository for accessing the {@link Wallet} table using JPA methods.
     */
    @Autowired
    private WalletRepository walletRepository;

    /**
     * Retrieves a user by their ID if the requesting user has permission to view the details.
     *
     * @param getUserInfoInDTO the DTO containing the ID of the user to retrieve and the ID of the requesting user
     * @return the {@link User} entity if the requesting user is authorized to view it
     * @throws ResourceNotFoundException if the user or requesting user is not found
     * @throws ResourceNotValidException if the requesting user does not have permission to view the requested user
     */
    @Override
    public User getById(final GetUserInfoInDTO getUserInfoInDTO) {
        try {
            User user = userRepository.findById(getUserInfoInDTO.getUserId());
            User loggedInUser = userRepository.findById(getUserInfoInDTO.getLoggedInUserId());
            if (user == null || loggedInUser == null) {
                log.error("User or logged-in user not found with IDs: {} and {}",
                        getUserInfoInDTO.getUserId(), getUserInfoInDTO.getLoggedInUserId());
                throw new ResourceNotFoundException("User not Found");
            }
            if (!user.equals(loggedInUser)) {
                log.error("User access denied for ID: {}", getUserInfoInDTO.getLoggedInUserId());
                throw new ResourceNotValidException("You Cannot view this user");
            }
            return user;
        } catch (Exception e) {
            log.error("Unexpected error while retrieving user by ID: {}", e.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }

    /**
     * Retrieves profile information for a user based on their user ID.
     *
     * @param userId the ID of the user whose profile information is to be retrieved
     * @return a {@link ProfileOutDTO} containing the user's profile information
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public ProfileOutDTO getProfileInfo(final long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User Not Found");
        }
        ProfileOutDTO profileOutDTO = new ProfileOutDTO();
        profileOutDTO.setName(user.getName());
        profileOutDTO.setEmail(user.getEmail());
        profileOutDTO.setPhone(user.getPhone());
        if (user.getRole() == Role.USER) {
            Wallet wallet = walletRepository.findByUserId(userId);
            profileOutDTO.setWalletAmount(wallet.getAmount());
        }
        return profileOutDTO;
    }

    /**
     * Retrieves a user by their identity (user ID).
     *
     * @param userId the ID of the user to retrieve
     * @return the {@link User} entity with the specified ID
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public User getByIdentity(final long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    /**
     * Registers a new user in the system.
     *
     * @param userInDTO the DTO containing the user details to be registered
     * @return a {@link String} message indicating the result of the registration
     * @throws ResourceAlreadyExistsException if a user with the provided email already exists
     * @throws RuntimeException if an unexpected error occurs while saving the user
     */
    @Override
    public String registerUser(final UserInDTO userInDTO) {
        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("Email already in use: {}", user.getEmail());
            throw new ResourceAlreadyExistsException(Constants.EMAIL_ALREADY_IN_USE);
        }
        try {
            userRepository.save(user);
            if (userInDTO.getRole() == Role.USER) {
                User addedUser = userRepository.findByEmail(userInDTO.getEmail());
                Wallet wallet = new Wallet();
                wallet.setUserId(addedUser.getUserId());
                wallet.setAmount(Constants.INITIAL_WALLET_AMOUNT);
                walletRepository.save(wallet);
                return Constants.USER_SIGNUP_MESSAGE;
            }
            return Constants.OWNER_SIGNUP_MESSAGE;
        } catch (Exception e) {
            log.error("Unexpected error while registering user: {}", e.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }

    /**
     * Authenticates a user based on their login credentials.
     *
     * @param loginRequestInDTO the DTO containing the user's login credentials (email and password)
     * @return a {@link LoginResponseOutDTO} containing user details if authentication is successful or an error message
     */
    @Override
    public LoginResponseOutDTO loginUser(final LoginRequestInDTO loginRequestInDTO) {
        try {
            User user = userRepository.findByEmailAndPassword(
                    loginRequestInDTO.getEmail(),
                    loginRequestInDTO.getPassword()
            );
            if (user == null) {
                log.error("Invalid login credentials for email: {}", loginRequestInDTO.getEmail());
                throw new ResourceNotValidException("Invalid Credentials");
            }
            return UserConverters.userEntityToLoginResponseOutDTO(user);
        } catch (ResourceNotValidException e) {
            log.error("Invalid credentials: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during login: {}", e.getMessage());
            throw new RuntimeException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }

    /**
     * Updates the profile information of a user.
     *
     * @param userId the ID of the user to be updated
     * @param updateProfileInDTO the DTO containing the updated profile information
     * @return a {@link String} message indicating the result of the update operation
     * @throws ResourceNotFoundException if the user to be updated is not found
     * @throws ResourceAlreadyExistsException if the email in the updated profile already exists
     */
    @Override
    public String updateUserProfile(final long userId, final UpdateProfileInDTO updateProfileInDTO) {
        User user = userRepository.findById(userId);
        if (user == null) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User Not Found");
        }
        if (!Objects.equals(user.getEmail(), updateProfileInDTO.getEmail())) {
            User emailExists = userRepository.findByEmail(updateProfileInDTO.getEmail());
            if (emailExists != null) {
                log.error("Email already exists: {}", updateProfileInDTO.getEmail());
                throw new ResourceAlreadyExistsException("Email Already Exists");
            }
        }
        user.setEmail(updateProfileInDTO.getEmail().trim().toLowerCase().replaceAll("\\s+", " "));
        user.setName(updateProfileInDTO.getName().trim().replaceAll("\\s+", " "));
        user.setPhone(updateProfileInDTO.getPhone().trim());
        userRepository.save(user);
        return "Profile Updated Successfully";
    }

/**
 * Sends a contact us message to the specified email address.
 *
 * @param contactUsInDTO the DTO containing the contact message details (subject, message,
 * Sends a contact us message to the specified email address.
 *
 * @return a {@link String} message indicating the result of the send operation
 * @throws RuntimeException if an error occurs while sending the email
 */
@Override
public String contactUs(final ContactUsInDTO contactUsInDTO) {
    try {
        sendEmail(contactUsInDTO);
        return "Your message has been sent successfully!";
    } catch (MessagingException e) {
        log.error("Error while sending email: {}", e.getMessage());
        throw new RuntimeException("Error while sending email: " + e.getMessage());
    }
}

    /**
     * Handles forgot password.
     * @param email of the user
     * @return a string message
     */
    @Override
    public String forgotPassword(final String email) {
        try {
            sendPasswordEmail(email);
            return "Your Password has been sent successfully!";
        } catch (MessagingException e) {
            log.error("Error while sending password email: {}", e.getMessage());
            throw new RuntimeException("Error while sending email: " + e.getMessage());
        }
    }

    /**
     * Sends an email using the {@link JavaMailSender}.
     *
     * @param contactUsDTO the DTO containing the email details (subject, message, from email, etc.)
     * @throws MessagingException if an error occurs while creating or sending the email
     */
    private void sendEmail(final ContactUsInDTO contactUsDTO) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String[] recipientsMails = {
                    "akashtiwariofficial2003@gmail.com",
                    "tiwari2003akash@gmail.com",
                    "work.akashtiwari@gmail.com"
            };
            helper.setReplyTo(contactUsDTO.getFromEmail());
            helper.setTo(recipientsMails);
            helper.setSubject(contactUsDTO.getSubject());
            helper.setText(contactUsDTO.getMessage(), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send contact email: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Sends an email using the {@link JavaMailSender}.
     *
     * @param email the email ID of the user
     * @throws MessagingException if an error occurs while creating or sending the email
     */
    private void sendPasswordEmail(final String email) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            User user = userRepository.findByEmail(email);
            if (user == null) {
                log.error("User not found with email: {}", email);
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            helper.setTo(email);
            helper.setSubject(Constants.PASSWORD_RESET);
            helper.setText(PasswordDecoder.decodeBase64Password(user.getPassword()), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to: {}", email);
            throw e;
        }
    }
}
