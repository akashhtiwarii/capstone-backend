package com.capstone.users_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Objects;

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

    /**
     * Override equals method.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressRequestInDTO that = (AddressRequestInDTO) o;
        return Objects.equals(email, that.email);
    }

    /**
     * Override hashcode method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
