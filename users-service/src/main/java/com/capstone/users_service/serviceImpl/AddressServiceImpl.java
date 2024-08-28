package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.AddressNotFoundException;
import com.capstone.users_service.exceptions.UserNotFoundException;
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
     * Get list of addresses added by user.
     *
     * @param addressRequestInDTO request body
     * @return list of addresses of a user
     */
    @Override
    public List<Address> findUserAddresses(AddressRequestInDTO addressRequestInDTO) {
        User user = userRepository.findByEmail(addressRequestInDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + addressRequestInDTO.getEmail());
        }
        List<Address> addresses = addressRepository.findByUserId(user.getUserId());
        if (addresses == null || addresses.isEmpty()) {
            throw new AddressNotFoundException("No addresses found for user with email: "
                    + addressRequestInDTO.getEmail());
        }
        return addresses;
    }

    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @Override
    public String addAddress(AddressInDTO addressInDTO) {
        User user = userRepository.findByEmail(addressInDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + addressInDTO.getEmail());
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
}
