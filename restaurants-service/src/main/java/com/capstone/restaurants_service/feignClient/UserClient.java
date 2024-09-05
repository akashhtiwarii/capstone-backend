package com.capstone.restaurants_service.feignClient;

import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User Service Connectivity.
 */
@FeignClient(name = Constants.USER_SERVICE, url = Constants.USER_SERVICE_ENDPOINT)
public interface UserClient {
    /**
     * Get User by Id.
     * @param userId
     * @return User
     */
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);
}
