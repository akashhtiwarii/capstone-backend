package com.capstone.users_service.utils;

import java.util.Base64;

/**
 * Utility class for decoding Base64-encoded passwords.
 */
public class PasswordDecoder {

    /**
     * Decodes a Base64-encoded password.
     *
     * @param base64EncodedPassword the Base64-encoded password to be decoded
     * @return the decoded password as a plain text string
     */
    public static String decodeBase64Password(String base64EncodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedPassword);
        return new String(decodedBytes);
    }
}
