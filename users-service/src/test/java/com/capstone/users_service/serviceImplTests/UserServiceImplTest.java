package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.dto.*;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.entity.Wallet;
import com.capstone.users_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.exceptions.ResourceNotValidException;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.repository.WalletRepository;
import com.capstone.users_service.serviceImpl.UserServiceImpl;
import com.capstone.users_service.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByIdSuccess() {
        GetUserInfoInDTO getUserInfoInDTO = new GetUserInfoInDTO();
        getUserInfoInDTO.setUserId(1L);
        getUserInfoInDTO.setLoggedInUserId(1L);

        User user = new User();
        user.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(user);

        User result = userService.getById(getUserInfoInDTO);

        assertEquals(user, result);
    }

    @Test
    public void testGetProfileInfoSuccess() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setRole(Role.USER);

        Wallet wallet = new Wallet();
        wallet.setAmount(100.0);

        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        ProfileOutDTO result = userService.getProfileInfo(userId);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPhone(), result.getPhone());
        assertEquals(wallet.getAmount(), result.getWalletAmount());
    }

    @Test
    public void testGetProfileInfoUserNotFound() {
        long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getProfileInfo(userId));
    }

    @Test
    public void testGetByIdentitySuccess() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(user);

        User result = userService.getByIdentity(userId);

        assertEquals(user, result);
    }

    @Test
    public void testGetByIdentityNotFound() {
        long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getByIdentity(userId));
    }

    @Test
    public void testRegisterUserSuccess() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("john.doe@example.com");
        userInDTO.setRole(Role.USER);
        userInDTO.setName("John Doe ");
        userInDTO.setPassword("password123 ");
        userInDTO.setPhone("1234567890 ");


        User user = new User();
        user.setEmail("john.doe@example.com");

        Mockito.when(userRepository.existsByEmail(userInDTO.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findByEmail(userInDTO.getEmail())).thenReturn(user);

        String result = userService.registerUser(userInDTO);

        assertEquals(Constants.USER_SIGNUP_MESSAGE, result);
    }

    @Test
    public void testRegisterUserEmailAlreadyExists() {
        UserInDTO userInDTO = new UserInDTO();
        userInDTO.setEmail("john.doe@example.com");
        userInDTO.setName("John Doe ");
        userInDTO.setPassword("password123 ");
        userInDTO.setRole(Role.USER);
        userInDTO.setPhone("1234567890 ");

        Mockito.when(userRepository.existsByEmail(userInDTO.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.registerUser(userInDTO));
    }

    @Test
    public void testLoginUserSuccess() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("john.doe@example.com");
        loginRequestInDTO.setPassword("password");

        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        Mockito.when(userRepository.findByEmailAndPassword(
                loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword()
        )).thenReturn(user);

        LoginResponseOutDTO result = userService.loginUser(loginRequestInDTO);

        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getPhone(), result.getPhone());
    }

    @Test
    public void testLoginUserInvalidCredentials() {
        LoginRequestInDTO loginRequestInDTO = new LoginRequestInDTO();
        loginRequestInDTO.setEmail("john.doe@example.com");
        loginRequestInDTO.setPassword("wrongpassword");

        Mockito.when(userRepository.findByEmailAndPassword(
                loginRequestInDTO.getEmail(), loginRequestInDTO.getPassword()
        )).thenReturn(null);

        LoginResponseOutDTO result = userService.loginUser(loginRequestInDTO);

        assertEquals(Constants.INVALID_CREDENTIALS, result.getMessage());
    }

    @Test
    public void testUpdateUserProfileSuccess() {
        long userId = 1L;
        UpdateProfileInDTO updateProfileInDTO = new UpdateProfileInDTO();
        updateProfileInDTO.setEmail("new.email@example.com");
        updateProfileInDTO.setName("New Name");
        updateProfileInDTO.setPhone("0987654321");

        User user = new User();
        user.setUserId(userId);
        user.setEmail("old.email@example.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(userRepository.findByEmail(updateProfileInDTO.getEmail())).thenReturn(null);

        String result = userService.updateUserProfile(userId, updateProfileInDTO);

        assertEquals("Profile Updated Successfully", result);
        assertEquals(updateProfileInDTO.getEmail(), user.getEmail());
        assertEquals(updateProfileInDTO.getName(), user.getName());
        assertEquals(updateProfileInDTO.getPhone(), user.getPhone());
    }

    @Test
    public void testUpdateUserProfileUserNotFound() {
        long userId = 1L;
        UpdateProfileInDTO updateProfileInDTO = new UpdateProfileInDTO();

        Mockito.when(userRepository.findById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUserProfile(userId, updateProfileInDTO));
    }

    @Test
    public void testUpdateUserProfileEmailAlreadyExists() {
        long userId = 1L;
        UpdateProfileInDTO updateProfileInDTO = new UpdateProfileInDTO();
        updateProfileInDTO.setEmail("existing.email@example.com");

        User user = new User();
        user.setUserId(userId);
        user.setEmail("old.email@example.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(userRepository.findByEmail(updateProfileInDTO.getEmail())).thenReturn(new User());
        assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUserProfile(userId, updateProfileInDTO));
    }

    @Test
    public void testContactUsSuccess() throws MessagingException {
        ContactUsInDTO contactUsInDTO = new ContactUsInDTO();
        contactUsInDTO.setFromEmail("john.doe@example.com");
        contactUsInDTO.setRestaurantEmail("restaurant@example.com");
        contactUsInDTO.setSubject("Inquiry");
        contactUsInDTO.setMessage("Hello, I would like to inquire about...");

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        userService.contactUs(contactUsInDTO);

        Mockito.verify(javaMailSender).send(mimeMessage);
    }
}
