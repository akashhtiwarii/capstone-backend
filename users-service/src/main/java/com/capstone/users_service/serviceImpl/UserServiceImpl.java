package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.users_service.converters.UserConverters;
import static com.capstone.users_service.utils.Constants.EMAIL_ALREADY_IN_USE;
import static com.capstone.users_service.utils.Constants.INITIAL_WALLET_AMOUNT;
import static com.capstone.users_service.utils.Constants.INVALID_CREDENTIALS;
import static com.capstone.users_service.utils.Constants.OWNER_SIGNUP_MESSAGE;
import static com.capstone.users_service.utils.Constants.UNEXPECTED_ERROR;
import static com.capstone.users_service.utils.Constants.USER_SIGNUP_MESSAGE;

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
     * Save user to database method implementation.
     * @param userInDTO request object
     * @return message after saving user
     */
    @Override
    public String save(UserInDTO userInDTO) {
        User user = UserConverters.registerUserInDTOToUserEntity(userInDTO);
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new EmailAlreadyExistsException(EMAIL_ALREADY_IN_USE);
            }
            try {
                userRepository.save(user);
                if (userInDTO.getRole() == Role.USER) {
                    User addedUser = userRepository.findByEmail(userInDTO.getEmail());
                    Wallet wallet = new Wallet();
                    wallet.setUserId(addedUser.getUserId());
                    wallet.setAmount(INITIAL_WALLET_AMOUNT);
                    walletRepository.save(wallet);
                    return USER_SIGNUP_MESSAGE;
                }
                return OWNER_SIGNUP_MESSAGE;
            } catch (Exception e) {
            throw new RuntimeException(UNEXPECTED_ERROR + e.getMessage());
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
            loginResponseOutDTO.setMessage(INVALID_CREDENTIALS);
            return loginResponseOutDTO;
        }
    }
}
