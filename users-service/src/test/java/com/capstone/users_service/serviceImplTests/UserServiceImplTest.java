package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.LoginRequestInDTO;
import com.capstone.users_service.InDTO.UserInDTO;
import com.capstone.users_service.OutDTO.LoginResponseOutDTO;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.EmailAlreadyExistsException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Success() {
        UserInDTO userInDTO = new UserInDTO("John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", Role.USER);
        User user = new User(1L, "John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", Role.USER);

        when(userRepository.existsByEmail(userInDTO.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<String> response = userServiceImpl.save(userInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account Added Successfully", response.getBody());

        verify(userRepository, times(1)).existsByEmail(userInDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSave_EmailAlreadyExists() {
        UserInDTO userInDTO = new UserInDTO("John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", Role.USER);
        when(userRepository.existsByEmail(userInDTO.getEmail())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.save(userInDTO));
        verify(userRepository, times(1)).existsByEmail(userInDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO("john.doe@example.com", "password123");
        User user = new User(1L, "John Doe", "john.doe@example.com", "password123", "1234567890", "123 Main St", Role.USER);

        when(userRepository.findByEmailAndPassword(loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword())).thenReturn(user);

        ResponseEntity<LoginResponseOutDTO> response = userServiceImpl.loginUser(loginRequestInDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login Successful", response.getBody().getMessage());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        assertEquals(user.getName(), response.getBody().getName());

        verify(userRepository, times(1)).findByEmailAndPassword(loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO("john.doe@example.com", "wrongpassword");

        when(userRepository.findByEmailAndPassword(loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword())).thenReturn(null);

        ResponseEntity<LoginResponseOutDTO> response = userServiceImpl.loginUser(loginRequestInDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid Credentials", response.getBody().getMessage());

        verify(userRepository, times(1)).findByEmailAndPassword(loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword());
    }
}
