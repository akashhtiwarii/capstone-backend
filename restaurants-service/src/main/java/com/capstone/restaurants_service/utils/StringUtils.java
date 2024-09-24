package com.capstone.restaurants_service.utils;

/**
 * Utility class for performing string operations.
 * <p>
 * This class provides static methods for common string manipulations.
 * </p>
 */
public class StringUtils {

    /**
     * Capitalizes the first letter of the given string and converts the rest of the string to lowercase.
     * <p>
     * If the input string is null or empty, it will be returned as is.
     * </p>
     *
     * @param input the string to be modified. Can be null or empty.
     * @return a string with the first letter capitalized and the rest of the letters in lowercase.
     *         If the input is null or empty, the same value is returned.
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
