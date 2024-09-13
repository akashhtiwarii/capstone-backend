package com.capstone.restaurants_service.feignClient;

import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for interacting with the User Service.
 * <p>
 * This interface is used to define the methods for communicating with the User Service
 * through HTTP requests. It leverages Feign to automatically handle the details of
 * making HTTP calls and receiving responses.
 * </p>
 */
@FeignClient(name = Constants.USER_SERVICE, url = Constants.USER_SERVICE_ENDPOINT)
public interface UserClient {

    /**
     * Retrieves user information by user ID from the User Service.
     * <p>
     * This method sends a GET request to the User Service to fetch details of a user
     * specified by the provided user ID.
     * </p>
     * @param userId the ID of the user to retrieve
     * @return a {@link ResponseEntity} containing the user details encapsulated in a {@link UserOutDTO}
     */
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);
}
