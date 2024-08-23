package com.capstone.users_service.service;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestDTO;
import com.capstone.users_service.OutDTO.AddressOutDTO;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    /**
     * Get list of addresses added by user.
     * @param addressRequestDTO request body
     * @return list of addresses of a user
     */
    AddressOutDTO findUserAddresses(AddressRequestDTO addressRequestDTO);

    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return String message
     */
    ResponseEntity<String> addAddress(AddressInDTO addressInDTO);
}
