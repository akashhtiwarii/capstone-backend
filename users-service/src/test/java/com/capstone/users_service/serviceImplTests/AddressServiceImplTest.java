package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.AddressRequestInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.AddressNotFoundException;
import com.capstone.users_service.exceptions.UserNotFoundException;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.serviceImpl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserAddressesUserNotFound() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO();
        addressRequestInDTO.setEmail("nonexistent@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            addressService.findUserAddresses(addressRequestInDTO);
        });
    }

    @Test
    void testFindUserAddressesAddressNotFound() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO();
        addressRequestInDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setUserId(1);

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(addressRepository.findByUserId(user.getUserId())).thenReturn(new ArrayList<>());

        assertThrows(AddressNotFoundException.class, () -> {
            addressService.findUserAddresses(addressRequestInDTO);
        });
    }

    @Test
    void testFindUserAddressesSuccess() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO();
        addressRequestInDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setUserId(1);

        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(addressRepository.findByUserId(user.getUserId())).thenReturn(addresses);

        List<Address> result = addressService.findUserAddresses(addressRequestInDTO);

        assertEquals(1, result.size());
    }

    @Test
    void testAddAddressUserNotFound() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("nonexistent@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            addressService.addAddress(addressInDTO);
        });
    }

    @Test
    void testAddAddressSuccess() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("test@gmail.com");
        addressInDTO.setAddress("ABC Address");
        addressInDTO.setPincode(12345L);
        addressInDTO.setCity("Test City");
        addressInDTO.setState("Test State");

        User user = new User();
        user.setUserId(1);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        String result = addressService.addAddress(addressInDTO);

        assertEquals("Address added successfully", result);
    }

    @Test
    void testAddAddressException() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("test@gmail.com");
        addressInDTO.setAddress("ABC Address");
        addressInDTO.setPincode(12345L);
        addressInDTO.setCity("Test City");
        addressInDTO.setState("Test State");

        User user = new User();
        user.setUserId(1);

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(addressRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            addressService.addAddress(addressInDTO);
        });
    }
}
