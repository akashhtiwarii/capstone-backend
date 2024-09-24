package com.capstone.users_service.service;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.entity.Address;

import java.util.List;

/**
 * Service interface for managing {@link Address} entities.
 * Defines methods for performing CRUD operations and other address-related operations.
 */
public interface AddressService {

    /**
     * Adds a new address to the system.
     *
     * @param addressInDTO the data transfer object containing the address details to be added
     * @return a {@link String} message indicating the result of the operation
     */
    String addAddress(AddressInDTO addressInDTO);

    /**
     * Retrieves a list of addresses associated with the specified user ID.
     *
     * @param userId the ID of the user whose addresses are to be retrieved
     * @return a {@link List} of {@link Address} entities associated with the specified user ID
     */
    List<Address> getAddressByUserId(long userId);

    /**
     * Retrieves an address by its unique identifier.
     *
     * @param addressId the ID of the address to be retrieved
     * @return the {@link Address} entity with the specified ID, or {@code null} if no such entity exists
     */
    Address getAddressById(long addressId);

    /**
     * Updates an existing address with the provided details.
     *
     * @param updateAddressInDTO the data transfer object containing the updated address details
     * @return a {@link String} message indicating the result of the operation
     */
    String updateAddress(UpdateAddressInDTO updateAddressInDTO);

    /**
     * Deletes an address associated with the specified user ID and address ID.
     *
     * @param userId the ID of the user whose address is to be deleted
     * @param addressId the ID of the address to be deleted
     * @return a {@link String} message indicating the result of the operation
     */
    String deleteAddress(long userId, long addressId);
}
