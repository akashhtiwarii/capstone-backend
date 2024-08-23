package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.InDTO.LoginRequestDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    /**
     * userRepository for accessing users table using Jpa methods.
     */
    @Autowired
    private UserRepository userRepository;

    private User registerUserInDTOToUserEntity(UserInDTO userInDTO) {
        User user = new User();
        user.setName(userInDTO.getName());
        user.setPassword(userInDTO.getPassword());
        user.setEmail(userInDTO.getEmail());
        user.setRole(userInDTO.getRole());
        user.setPhone(userInDTO.getPhone());
        user.setAddress(userInDTO.getAddress());
        return user;
    }

    /**
     * Save user to database method implementation.
     * @param userInDTO request object
     * @return message after saving user
     */
    @Override
    public ResponseEntity<String> save(UserInDTO userInDTO) {
        User user = registerUserInDTOToUserEntity(userInDTO);
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email is already in use");
            }
            try {
                userRepository.save(user);
                return ResponseEntity.ok("Account Added Successfully");
            } catch (Exception e) {
            throw new RuntimeException("An unexpected error occured: " + e.getMessage());
        }
    }
    /**
     * User Login.
      * @param loginRequestDTO login credentials
     * @return User details if exists.
     */
    @Override
    public ResponseEntity<LoginResponseDTO> loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (user != null) {
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                    user.getUserId(),
                    user.getEmail(),
                    user.getName(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getRole(),
                    "Login Successful");
            return ResponseEntity.ok(loginResponseDTO);
        } else {
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setMessage("Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponseDTO);
        }
    }
}
