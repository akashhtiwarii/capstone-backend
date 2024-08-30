package com.capstone.restaurants_service.utils;

public class StringUtils {
    /**
     * Capitalize first letter of a string.
     * @param input
     * @return String with 1st letter capital
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}


