package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOwnerRestaurantsInDTO {
    /**
     * OwnerID.
     */
    @NotNull(message = "A valid Owner ID Required")
    @Min(value = 1, message = "A valid Owner ID Required")
    private long ownerId;

    /**
     * Override equals.
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetOwnerRestaurantsInDTO that = (GetOwnerRestaurantsInDTO) o;
        return ownerId == that.ownerId;
    }
    /**
     * Override hashcode.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(ownerId);
    }
}
