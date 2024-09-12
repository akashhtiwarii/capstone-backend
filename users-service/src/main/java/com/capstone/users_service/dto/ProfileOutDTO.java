package com.capstone.users_service.dto;

import com.capstone.users_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileOutDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private long pincode;
    private String state;
    private double walletAmount;
}
