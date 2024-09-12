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

@FeignClient(name = "users-service", url = "http://localhost:8081/user")
public interface UsersFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<UserOutDTO> getUserById(@PathVariable("id") long userId);
    @GetMapping("/address/id")
    ResponseEntity<AddressOutDTO> getAddressById(@RequestParam long addressId);
    @GetMapping("/address")
    ResponseEntity<List<AddressOutDTO>> getAddressByUserId(@RequestParam long userId);
    @GetMapping("/wallet")
    ResponseEntity<WalletOutDTO> getUserWallet(@RequestParam long userId);
    @PutMapping("/wallet/update")
    ResponseEntity<String> updateUserWallet(@RequestParam long userId, @RequestParam double amount);
}
