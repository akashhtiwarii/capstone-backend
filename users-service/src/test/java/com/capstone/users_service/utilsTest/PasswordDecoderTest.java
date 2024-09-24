package com.capstone.users_service.utilsTest;

import com.capstone.users_service.utils.PasswordDecoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordDecoderTest {

    @Test
    public void testDecodeBase64Password_ValidInput() {
        String base64EncodedPassword = "cGFzc3dvcmQ=";
        String expected = "password";
        String actual = PasswordDecoder.decodeBase64Password(base64EncodedPassword);
        assertEquals(expected, actual);
    }

    @Test
    public void testDecodeBase64Password_EmptyInput() {
        String base64EncodedPassword = "";
        String expected = "";
        String actual = PasswordDecoder.decodeBase64Password(base64EncodedPassword);
        assertEquals(expected, actual);
    }

    @Test
    public void testDecodeBase64Password_InvalidInput() {
        String base64EncodedPassword = "invalid_base64";
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordDecoder.decodeBase64Password(base64EncodedPassword);
        });
    }
}

