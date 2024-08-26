package com.capstone.users_service.InDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * AddressRequestInDTO for providing email to get all addresses for that user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestInDTO {
    /**
     * User email Id.
     */
    @Email(message = "No valid email found")
    private String email;
}
