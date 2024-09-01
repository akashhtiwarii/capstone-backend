package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.GetUserInfoInDTO;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.exceptions.UserNotFoundException;
import com.capstone.users_service.exceptions.UserNotValidException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.users_service.converters.UserConverters;
import com.capstone.users_service.utils.Constants;

/**
 * UserServiceImpl for implementing methods of UserService.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * userRepository for accessing users table using Jpa methods.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * walletRepository for accessing wallet table using Jpd methods.
     */
    @Autowired
    private WalletRepository walletRepository;

    /**
     * Get User by Id.
     * @param getUserInfoInDTO
     * @return User
     */
    @Override
    public User getById(GetUserInfoInDTO getUserInfoInDTO) {
        try {
            User user = userRepository.findById(getUserInfoInDTO.getUserId());
            User loggedInUser = userRepository.findById(getUserInfoInDTO.getLoggedInUserId());
            if (user == null || loggedInUser == null) {
                throw new UserNotFoundException("User not Found");
            }
            if (user != loggedInUser) {
                throw new UserNotValidException("You Cannot view this user");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR + e.getMessage());
        }
    }

    /**
     * Get by identity.
     * @param userId
     * @return user
     */
    @Override
    public User getByIdentity(long userId) {
        return userRepository.findById(userId);
    }


    /**
     * Save user to database method implementation.
     * @param userInDTO request object
     * @return message after saving user
     */
    @Override
    public String registerUser(UserInDTO userInDTO) {
        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new EmailAlreadyExistsException(Constants.EMAIL_ALREADY_IN_USE);
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
     * User Login.
      * @param loginRequestInDTO login credentials
     * @return User details if exists.
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
}
