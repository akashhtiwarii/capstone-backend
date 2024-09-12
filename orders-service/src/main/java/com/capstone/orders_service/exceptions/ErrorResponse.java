package com.capstone.orders_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /**
     * Error Status Code.
     */
    private int status;
    /**
     * Error Message.
     */
    private String message;

    /**
     * Override equals method.
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
        ErrorResponse that = (ErrorResponse) o;
        return status == that.status && Objects.equals(message, that.message);
    }

    /**
     * Override hashcode method.
     * @return hashed object
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}
