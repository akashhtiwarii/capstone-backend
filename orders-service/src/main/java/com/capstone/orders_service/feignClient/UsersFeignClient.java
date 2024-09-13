package com.capstone.orders_service.feignClient;

import com.capstone.orders_service.dto.AddressOutDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.dto.WalletOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign Client interface for communicating with the user service.
 * <p>
 * This interface defines methods for accessing user-related data from the user service using Feign,
 * which is a declarative HTTP client for Spring Boot. It allows you to make HTTP requests to the user service
 * and retrieve information about users, addresses, and wallets.
 * </p>
 */
@FeignClient(name = "users-service", url = "http://localhost:8081/user")
public interface UsersFeignClient {

    /**
     * Retrieves details of a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link UserOutDTO} object with the user details
     * or an error response if the user is not found.
     */
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);

    /**
     * Retrieves details of an address by its unique identifier.
     *
     * @param addressId The unique identifier of the address to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link AddressOutDTO} object with the address details
     * or an error response if the address is not found.
     */
    @GetMapping("/address/id")
    ResponseEntity<AddressOutDTO> getAddressById(@RequestParam long addressId);

    /**
     * Retrieves a list of addresses associated with a specific user.
     *
     * @param userId The unique identifier of the user whose addresses are to be retrieved.
     * @return A {@link ResponseEntity} containing a {@link List} of {@link AddressOutDTO} objects with the addresses
     * or an error response if no addresses are found for the user.
     */
    @GetMapping("/address")
    ResponseEntity<List<AddressOutDTO>> getAddressByUserId(@RequestParam long userId);

    /**
     * Retrieves the wallet details of a specific user.
     *
     * @param userId The unique identifier of the user whose wallet details are to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link WalletOutDTO} object with the wallet details
     * or an error response if the wallet is not found for the user.
     */
    @GetMapping("/wallet")
    ResponseEntity<WalletOutDTO> getUserWallet(@RequestParam long userId);

    /**
     * Updates the wallet balance of a specific user.
     *
     * @param userId The unique identifier of the user whose wallet balance is to be updated.
     * @param amount The amount to be updated in the user's wallet.
     * @return A {@link ResponseEntity} containing a {@link String} message indicating the result of the update operation.
     */
    @PutMapping("/wallet/update")
    ResponseEntity<String> updateUserWallet(@RequestParam long userId, @RequestParam double amount);
}
