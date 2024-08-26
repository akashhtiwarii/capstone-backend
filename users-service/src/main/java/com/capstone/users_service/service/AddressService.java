package com.capstone.users_service.service;

import com.capstone.users_service.InDTO.AddressInDTO;
import com.capstone.users_service.InDTO.AddressRequestInDTO;
import com.capstone.users_service.OutDTO.AddressOutDTO;

/**
 * AddressService for defining methods related to address table.
 */
public interface AddressService {
    /**
     * Get list of addresses added by user.
     * @param addressRequestInDTO request body
     * @return list of addresses of a user
     */
    AddressOutDTO findUserAddresses(AddressRequestInDTO addressRequestInDTO);

    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return String message
     */
    String addAddress(AddressInDTO addressInDTO);
}
