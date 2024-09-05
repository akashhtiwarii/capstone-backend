package com.capstone.restaurants_service.UtilsTest;
import static org.junit.jupiter.api.Assertions.*;

import com.capstone.restaurants_service.utils.StringUtils;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    void testCapitalizeFirstLetterNormalCase() {
        String input = "hello world";
        String result = StringUtils.capitalizeFirstLetter(input);
        assertEquals("Hello world", result);
    }

    @Test
    void testCapitalizeFirstLetterEmptyString() {
        String input = "";
        String result = StringUtils.capitalizeFirstLetter(input);
        assertEquals("", result);
    }

    @Test
    void testCapitalizeFirstLetterNullInput() {
        String input = null;
        String result = StringUtils.capitalizeFirstLetter(input);
        assertNull(result);
    }

    @Test
    void testCapitalizeFirstLetterSingleCharacter() {
        String input = "a";
        String result = StringUtils.capitalizeFirstLetter(input);
        assertEquals("A", result);
    }

    @Test
    void testCapitalizeFirstLetterAllUppercase() {
        String input = "JAVA";
        String result = StringUtils.capitalizeFirstLetter(input);
        assertEquals("Java", result);
    }

    @Test
    void testCapitalizeFirstLetterLeadingAndTrailingWhitespace() {
        String input = "   hello world   ";
        String result = StringUtils.capitalizeFirstLetter(input.trim());
        assertEquals("Hello world", result);
    }
}

