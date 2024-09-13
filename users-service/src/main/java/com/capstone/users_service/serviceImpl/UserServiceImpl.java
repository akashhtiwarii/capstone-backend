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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.capstone.users_service.converters.UserConverters;
import com.capstone.users_service.utils.Constants;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * Implementation of {@link UserService} for managing user-related operations.
 * This service handles user registration, profile updates, login,
 * and interactions related to user profiles and wallets.
 */
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
    public User getById(GetUserInfoInDTO getUserInfoInDTO) {
        try {
            User user = userRepository.findById(getUserInfoInDTO.getUserId());
            User loggedInUser = userRepository.findById(getUserInfoInDTO.getLoggedInUserId());
            if (user == null || loggedInUser == null) {
                throw new ResourceNotFoundException("User not Found");
            }
            if (!user.equals(loggedInUser)) {
                throw new ResourceNotValidException("You Cannot view this user");
            }
            return user;
        } catch (Exception e) {
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
    public ProfileOutDTO getProfileInfo(long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
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
    public User getByIdentity(long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
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
    public String registerUser(UserInDTO userInDTO) {
        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
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
    public LoginResponseOutDTO loginUser(LoginRequestInDTO loginRequestInDTO) {
        User user = userRepository.findByEmailAndPassword(
                loginRequestInDTO.getEmail(),
                loginRequestInDTO.getPassword()
        );
        if (user != null) {
            return UserConverters.userEntityToLoginResponseOutDTO(user);
        } else {
            LoginResponseOutDTO loginResponseOutDTO = new LoginResponseOutDTO();
            loginResponseOutDTO.setMessage(Constants.INVALID_CREDENTIALS);
            return loginResponseOutDTO;
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
    public String updateUserProfile(long userId, UpdateProfileInDTO updateProfileInDTO) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }
        if (!Objects.equals(user.getEmail(), updateProfileInDTO.getEmail())) {
            User emailExists = userRepository.findByEmail(updateProfileInDTO.getEmail());
            if (emailExists != null) {
                throw new ResourceAlreadyExistsException("Email Already Exists");
            }
        }
        user.setEmail(updateProfileInDTO.getEmail());
        user.setName(updateProfileInDTO.getName());
        user.setPhone(updateProfileInDTO.getPhone());
        userRepository.save(user);
        return "Profile Updated Successfully";
    }

    /**
     * Sends a contact us message to the specified email address.
     *
     * @param contactUsInDTO the DTO containing the contact message details (subject, message, from email, etc.)
     * @return a {@link String} message indicating the result of the send operation
     * @throws RuntimeException if an error occurs while sending the email
     */
    @Override
    public String contactUs(ContactUsInDTO contactUsInDTO) {
        try {
            sendEmail(contactUsInDTO);
            return "Your message has been sent successfully!";
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage());
        }
    }

    /**
     * Sends an email using the {@link JavaMailSender}.
     *
     * @param contactUsDTO the DTO containing the email details (subject, message, from email, etc.)
     * @throws MessagingException if an error occurs while creating or sending the email
     */
    private void sendEmail(ContactUsInDTO contactUsDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setReplyTo(contactUsDTO.getFromEmail());
        helper.setTo(contactUsDTO.getRestaurantEmail());
        helper.setSubject(contactUsDTO.getSubject());
        helper.setText(contactUsDTO.getMessage(), true);

        javaMailSender.send(message);
    }
}
