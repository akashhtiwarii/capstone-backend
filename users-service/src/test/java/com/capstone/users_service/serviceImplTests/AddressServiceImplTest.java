package com.capstone.users_service.serviceImplTests;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.exceptions.ResourceNotValidException;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.serviceImpl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    public AddressServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAddressSuccess() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setUserId(1L);
        addressInDTO.setAddress("123 Main St");
        addressInDTO.setPincode(12345);
        addressInDTO.setCity("City");
        addressInDTO.setState("State");

        User user = new User();
        user.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(new Address());

        String result = addressService.addAddress(addressInDTO);

        assertEquals("Address added successfully", result);
    }

    @Test
    public void testAddAddressUserNotFound() {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.addAddress(addressInDTO));
    }

    @Test
    public void testGetAddressByUserIdSuccess() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());

        Mockito.when(addressRepository.findByUserId(1L)).thenReturn(addresses);

        List<Address> result = addressService.getAddressByUserId(1L);

        assertEquals(addresses, result);
    }

    @Test
    public void testGetAddressByUserIdNotFound() {
        Mockito.when(addressRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressByUserId(1L));
    }

    @Test
    public void testGetAddressByIdSuccess() {
        Address address = new Address();

        Mockito.when(addressRepository.findById(1L)).thenReturn(address);

        Address result = addressService.getAddressById(1L);

        assertEquals(address, result);
    }

    @Test
    public void testGetAddressByIdNotFound() {
        Mockito.when(addressRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(1L));
    }

    @Test
    public void testUpdateAddressSuccess() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();
        updateAddressInDTO.setUserId(1L);
        updateAddressInDTO.setAddressId(1L);
        updateAddressInDTO.setAddress("456 New St");
        updateAddressInDTO.setPincode(678907);
        updateAddressInDTO.setCity("New City");
        updateAddressInDTO.setState("New State");

        User user = new User();
        user.setUserId(1L);

        Address address = new Address();
        address.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        Mockito.when(addressRepository.findById(1L)).thenReturn(address);
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(new Address());

        String result = addressService.updateAddress(updateAddressInDTO);

        assertEquals("Address Updated Successfully", result);
    }

    @Test
    public void testUpdateAddressUserNotFound() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();
        updateAddressInDTO.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(updateAddressInDTO));
    }

    @Test
    public void testUpdateAddressAddressNotFound() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();
        updateAddressInDTO.setAddressId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(new User());
        Mockito.when(addressRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(updateAddressInDTO));
    }

    @Test
    public void testUpdateAddressUserNotValid() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();
        updateAddressInDTO.setUserId(1L);
        updateAddressInDTO.setAddressId(1L);

        User user = new User();
        user.setUserId(2L);

        Address address = new Address();
        address.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        Mockito.when(addressRepository.findById(1L)).thenReturn(address);

        assertThrows(ResourceNotValidException.class, () -> addressService.updateAddress(updateAddressInDTO));
    }

    @Test
    public void testDeleteAddressSuccess() {
        Mockito.when(userRepository.findById(1L)).thenReturn(new User());
        Mockito.when(addressRepository.findById(1L)).thenReturn(new Address());

        String result = addressService.deleteAddress(1L, 1L);

        assertEquals("Address Deleted Successfully", result);
    }

    @Test
    public void testDeleteAddressUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L, 1L));
    }

    @Test
    public void testDeleteAddressAddressNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(new User());
        Mockito.when(addressRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L, 1L));
    }

    @Test
    public void testDeleteAddressUserNotValid() {
        User user = new User();
        user.setUserId(2L);

        Address address = new Address();
        address.setUserId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        Mockito.when(addressRepository.findById(1L)).thenReturn(address);

        assertThrows(ResourceNotValidException.class, () -> addressService.deleteAddress(1L, 1L));
    }
}