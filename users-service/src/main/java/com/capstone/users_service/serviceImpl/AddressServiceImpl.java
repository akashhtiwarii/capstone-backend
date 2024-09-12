package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByEmail(addressInDTO.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + addressInDTO.getEmail());
        }
        Address addressAlreadyExist = addressRepository.findByUserId(user.getUserId());
        if (addressAlreadyExist != null) {
            throw new ResourceAlreadyExistsException("Address Already Present for this user");
        }
        Address address = new Address();
        address.setUserId(user.getUserId());
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
    public Address getAddressById(long userId) {
        Address address = addressRepository.findByUserId(userId);
        if (address == null) {
           throw new ResourceNotFoundException("Address Not Found");
        }
        return address;
    }
}
