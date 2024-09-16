package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.dto.AddressInDTO;
import com.capstone.users_service.dto.UpdateAddressInDTO;
import com.capstone.users_service.entity.Address;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.ResourceNotFoundException;
import com.capstone.users_service.exceptions.ResourceNotValidException;
import com.capstone.users_service.repository.AddressRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link AddressService} for managing address-related operations.
 * This service provides methods for adding, updating, retrieving, and deleting addresses.
 */
@Service
public class AddressServiceImpl implements AddressService {

    /**
     * Repository for performing CRUD operations on the {@link Address} entity.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Repository for performing CRUD operations on the {@link User} entity.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new address based on the provided {@link AddressInDTO}.
     *
     * @param addressInDTO the DTO containing the details of the address to be added
     * @return a {@link String} message indicating the result of the operation
     * @throws ResourceNotFoundException if the user associated with the address does not exist
     * @throws RuntimeException if an unexpected error occurs while saving the address
     */
    @Override
    public String addAddress(AddressInDTO addressInDTO) {
        User user = userRepository.findById(addressInDTO.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + addressInDTO.getUserId());
        }
        Address address = new Address();
        address.setUserId(addressInDTO.getUserId());
        address.setAddress(addressInDTO.getAddress().trim());
        address.setPincode(addressInDTO.getPincode());
        address.setCity(addressInDTO.getCity().trim());
        address.setState(addressInDTO.getState().trim());
        try {
            addressRepository.save(address);
            return "Address added successfully";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of addresses associated with a specific user ID.
     *
     * @param userId the ID of the user whose addresses are to be retrieved
     * @return a {@link List} of {@link Address} entities associated with the specified user ID
     * @throws ResourceNotFoundException if no addresses are found for the specified user ID
     */
    @Override
    public List<Address> getAddressByUserId(long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("Address Not Found");
        }
        return addresses;
    }

    /**
     * Retrieves an address by its ID.
     *
     * @param addressId the ID of the address to be retrieved
     * @return the {@link Address} entity with the specified ID
     * @throws ResourceNotFoundException if no address is found with the specified ID
     */
    @Override
    public Address getAddressById(long addressId) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new ResourceNotFoundException("Address Not Found");
        }
        return address;
    }

    /**
     * Updates an existing address based on the provided {@link UpdateAddressInDTO}.
     *
     * @param updateAddressInDTO the DTO containing the updated details of the address
     * @return a {@link String} message indicating the result of the update operation
     * @throws ResourceNotFoundException if the user or address to be updated is not found
     * @throws ResourceNotValidException if the user ID associated with the address does not match the provided user ID
     */
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
        address.setAddress(updateAddressInDTO.getAddress().trim());
        address.setCity(updateAddressInDTO.getCity().trim());
        address.setPincode(updateAddressInDTO.getPincode());
        address.setState(updateAddressInDTO.getState().trim());
        addressRepository.save(address);
        return "Address Updated Successfully";
    }

    /**
     * Deletes an address based on the specified user ID and address ID.
     *
     * @param userId the ID of the user who owns the address
     * @param addressId the ID of the address to be deleted
     * @return a {@link String} message indicating the result of the delete operation
     * @throws ResourceNotFoundException if the user or address to be deleted is not found
     * @throws ResourceNotValidException if the user ID associated with the address does not match the provided user ID
     */
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
