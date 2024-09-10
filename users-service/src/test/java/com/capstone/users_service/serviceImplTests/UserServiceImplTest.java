package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.dto.LoginRequestInDTO;
import com.capstone.users_service.dto.UserInDTO;
import com.capstone.users_service.dto.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.capstone.users_service.utils.Constants.EMAIL_ALREADY_IN_USE;
import static com.capstone.users_service.utils.Constants.INVALID_CREDENTIALS;
import static com.capstone.users_service.utils.Constants.OWNER_SIGNUP_MESSAGE;
import static com.capstone.users_service.utils.Constants.USER_SIGNUP_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRestaurantUserExistsThrowsEmailAlreadyExistsException() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("test@gmail.com");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userServiceImpl.addRestaurant(userInDTO)
        );
        assertEquals(EMAIL_ALREADY_IN_USE, exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    public void testAddRestaurantNewUserSuccessUserRole() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("test@gmail.com");
        userInDTO.setRole(Role.USER);

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        String result = userServiceImpl.addRestaurant(userInDTO);

        assertEquals(USER_SIGNUP_MESSAGE, result);
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    public void testAddRestaurantNewUserSuccessOwnerRole() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("owner@gmail.com");
        userInDTO.setRole(Role.OWNER);

        User user = new User();
        user.setUserId(1L);
        user.setEmail("owner@gmail.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userServiceImpl.addRestaurant(userInDTO);

        assertEquals(OWNER_SIGNUP_MESSAGE, result);
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    public void testLoginUserSuccess() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("test@gmail.com");
        loginRequestInDTO.setPassword("password");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);

        LoginResponseOutDTO result = userServiceImpl.loginUser(loginRequestInDTO);

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }

    @Test
    public void testLoginUserFailureInvalidCredentials() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("wrong@example.com");
        loginRequestInDTO.setPassword("wrongpassword");

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);

        LoginResponseOutDTO result = userServiceImpl.loginUser(loginRequestInDTO);

        assertNotNull(result);
        assertEquals(INVALID_CREDENTIALS, result.getMessage());
        verify(userRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }
}

