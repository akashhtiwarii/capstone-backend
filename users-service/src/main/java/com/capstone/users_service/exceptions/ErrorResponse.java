package com.capstone.users_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
