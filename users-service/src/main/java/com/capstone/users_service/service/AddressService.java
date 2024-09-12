package com.capstone.users_service.service;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.entity.Address;

/**
 * AddressService for defining methods related to address table.
 */
public interface AddressService {
    /**
     * Add new address.
     * @param addressInDTO request parameter
     * @return String message
     */
    String addAddress(AddressInDTO addressInDTO);

    Address getAddressById(long userId);
}
