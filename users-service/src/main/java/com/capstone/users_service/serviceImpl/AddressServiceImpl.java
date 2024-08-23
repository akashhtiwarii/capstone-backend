package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestDTO;
import com.capstone.users_service.OutDTO.AddressOutDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
     * @param addressRequestDTO request body
     * @return list of addresses of a user
     */
    @Override
    public AddressOutDTO findUserAddresses(AddressRequestDTO addressRequestDTO) {
        User user = userRepository.findByEmail(addressRequestDTO.getEmail());
        AddressOutDTO addressOutDTO = new AddressOutDTO();
        addressOutDTO.setAddresses(addressRepository.findById(user.getUserId()));
        addressOutDTO.setCurrentAddress(user.getAddress());
        return addressOutDTO;
    }

    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return a string message
     */
    @Override
    public ResponseEntity<String> addAddress(AddressInDTO addressInDTO) {
        User user = userRepository.findByEmail(addressInDTO.getEmail());
        Address address = new Address();
        address.setUserId(user.getUserId());
        address.setAddress(addressInDTO.getAddress());
        address.setPincode(addressInDTO.getPincode());
        address.setCity(addressInDTO.getCity());
        address.setState(address.getState());
        try {
            addressRepository.save(address);
            return ResponseEntity.ok("Address added successfully");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occured: " + e.getMessage());
        }
    }
}
