package com.capstone.restaurants_service.feignClient;

import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "http://localhost:8080/user")
public interface UserClient {
    /**
     * Get User by Id.
     * @param userId
     * @return User
     */
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);
}
