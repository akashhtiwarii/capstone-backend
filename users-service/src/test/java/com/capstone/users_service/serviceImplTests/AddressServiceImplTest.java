package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.serviceImpl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private User user;
    private Address address;
    private AddressInDTO addressInDTO;
    private AddressRequestInDTO addressRequestInDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        address = new Address();
        address.setAddressId(1L);
        address.setUserId(1L);
        address.setAddress("123 Main St");
        address.setPincode(123456L);
        address.setCity("Test City");
        address.setState("Test State");

        addressInDTO = new AddressInDTO();
        addressInDTO.setEmail("test@example.com");
        addressInDTO.setAddress("123 Main St");
        addressInDTO.setPincode(123456L);
        addressInDTO.setCity("Test City");
        addressInDTO.setState("Test State");

        addressRequestInDTO = new AddressRequestInDTO();
        addressRequestInDTO.setEmail("test@example.com");
    }

    @Test
    void testFindUserAddresses_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(addressRepository.findByUserId(1L)).thenReturn(Arrays.asList(address));

        List<Address> addresses = addressService.findUserAddresses(addressRequestInDTO);

        assertNotNull(addresses);
        assertEquals(1, addresses.size());
        assertEquals("123 Main St", addresses.get(0).getAddress());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(addressRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testFindUserAddresses_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        List<Address> addresses = addressService.findUserAddresses(addressRequestInDTO);

        assertNotNull(addresses);
        assertTrue(addresses.isEmpty());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(addressRepository, never()).findByUserId(anyLong());
    }

    @Test
    void testAddAddress_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        String result = addressService.addAddress(addressInDTO);

        assertEquals("Address added successfully", result);

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testAddAddress_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            addressService.addAddress(addressInDTO);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testAddAddress_ExceptionDuringSave() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            addressService.addAddress(addressInDTO);
        });

        assertEquals("An unexpected error occurred: Database error", exception.getMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(addressRepository, times(1)).save(any(Address.class));
    }
}