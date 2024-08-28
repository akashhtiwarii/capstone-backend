package com.capstone.users_service.exceptions;

/**
 * Restaurant Not Found Exception.
 */
public class RestaurantsNotFoundException extends RuntimeException {
    /**
     * No Restaurants in database exception.
     * @param message error message
     */
    public RestaurantsNotFoundException(final String message) {
        super(message);
    }
}
