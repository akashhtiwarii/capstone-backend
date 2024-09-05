package com.capstone.restaurants_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Success Response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSuccessOutDTO {
    /**
     * Success Message.
     */
    private String message;

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
        RequestSuccessOutDTO that = (RequestSuccessOutDTO) o;
        return Objects.equals(message, that.message);
    }

    /**
     * Override Hashcode.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
