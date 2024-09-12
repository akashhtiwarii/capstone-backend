package com.capstone.users_service.service;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.entity.Address;

import java.util.List;

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

    List<Address> getAddressByUserId(long userId);

    Address getAddressById(long addressId);

    String updateAddress(UpdateAddressInDTO updateAddressInDTO);

    String deleteAddress(long userId, long addressId);
}
