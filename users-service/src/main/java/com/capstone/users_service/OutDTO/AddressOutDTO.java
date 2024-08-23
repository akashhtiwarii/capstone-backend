package com.capstone.users_service.OutDTO;

import com.capstone.users_service.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressOutDTO {
    /**
     * List of addresses.
     */
    private List<Address> addresses;
    /**
     * Current address from users table.
     */
    private String currentAddress;
}
