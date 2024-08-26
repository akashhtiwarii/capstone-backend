package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.OutDTO.AddressOutDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.serviceImpl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserAddresses() {
        AddressRequestInDTO addressRequestInDTO = new AddressRequestInDTO("john.doe@example.com");
        User user = new User();
        user.setUserId(1L);
        user.setAddress("123 Main St");

        when(userRepository.findByEmail(addressRequestInDTO.getEmail())).thenReturn(user);
        when(addressRepository.findById(user.getUserId())).thenReturn(Collections.singletonList(new Address()));

        AddressOutDTO result = addressServiceImpl.findUserAddresses(addressRequestInDTO);

        assertNotNull(result);
        assertEquals("123 Main St", result.getCurrentAddress());
        assertFalse(result.getAddresses().isEmpty());

        verify(userRepository, times(1)).findByEmail(addressRequestInDTO.getEmail());
        verify(addressRepository, times(1)).findById(user.getUserId());
    }

    @Test
    void testAddAddress() {
        AddressInDTO addressInDTO = new AddressInDTO("john.doe@example.com", "456 Elm St", 123456L, "Springfield", "IL");
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findByEmail(addressInDTO.getEmail())).thenReturn(user);
        when(addressRepository.save(any(Address.class))).thenReturn(new Address());

        ResponseEntity<String> response = addressServiceImpl.addAddress(addressInDTO);

        assertNotNull(response);
        assertEquals("Address added successfully", response.getBody());

        verify(userRepository, times(1)).findByEmail(addressInDTO.getEmail());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testAddAddress_Exception() {
        AddressInDTO addressInDTO = new AddressInDTO("john.doe@example.com", "456 Elm St", 123456L, "Springfield", "IL");
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findByEmail(addressInDTO.getEmail())).thenReturn(user);
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> addressServiceImpl.addAddress(addressInDTO));
        assertEquals("An unexpected error occured: Database error", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(addressInDTO.getEmail());
        verify(addressRepository, times(1)).save(any(Address.class));
    }


}

