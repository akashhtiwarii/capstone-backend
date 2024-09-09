package com.capstone.orders_service.feignClient;

import com.capstone.orders_service.dto.AddressInDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users-service", url = "http://localhost:8081/user")
public interface UsersFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);
    @GetMapping("/address")
    ResponseEntity<AddressInDTO> getAddressById(@RequestParam long userId);
}
