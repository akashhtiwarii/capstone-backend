package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.exceptions.ResourceNotValidException;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AddressServiceImpl for implementing methods of AddressService.
 */
@Service
public class AddressServiceImpl implements AddressService {

    /**
     * addressRepository for connecting with address table in database.
     */
    @Autowired
    private AddressRepository addressRepository;
    /**
     * userRepository for connecting with address table in database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @Override
    public String addAddress(AddressInDTO addressInDTO) {
        User user = userRepository.findById(addressInDTO.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + addressInDTO.getUserId());
        }
        Address address = new Address();
        address.setUserId(addressInDTO.getUserId());
        address.setAddress(addressInDTO.getAddress());
        address.setPincode(addressInDTO.getPincode());
        address.setCity(addressInDTO.getCity());
        address.setState(addressInDTO.getState());
        try {
            addressRepository.save(address);
            return "Address added successfully";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<Address> getAddressByUserId(long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.isEmpty()) {
           throw new ResourceNotFoundException("Address Not Found");
        }
        return addresses;
    }

    @Override
    public Address getAddressById(long addressId) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("Address Not Found");
        }
        return address;
    }

    @Override
    public String updateAddress(UpdateAddressInDTO updateAddressInDTO) {
        User user = userRepository.findById(updateAddressInDTO.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }
        Address address = addressRepository.findById(updateAddressInDTO.getAddressId());
        if (address == null) {
            throw new ResourceNotFoundException("Address Not Found");
        }
        if (user.getUserId() != address.getUserId()) {
            throw new ResourceNotValidException("You cannot update this address");
        }
        address.setAddress(updateAddressInDTO.getAddress());
        address.setCity(updateAddressInDTO.getCity());
        address.setPincode(updateAddressInDTO.getPincode());
        address.setState(updateAddressInDTO.getState());
        addressRepository.save(address);
        return "Address Updated Successfully";
    }

    @Override
    public String deleteAddress(long userId, long addressId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("Address Not Found");
        }
        if (user.getUserId() != address.getUserId()) {
            throw new ResourceNotValidException("You cannot delete this address");
        }
        addressRepository.deleteById(addressId);
        return "Address Deleted Successfully";
    }
}
