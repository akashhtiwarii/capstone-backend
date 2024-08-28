package com.capstone.users_service.exceptionsTest;

import com.capstone.users_service.exceptions.AddressNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressNotFoundExceptionTest {

    @Test
    void testAddressNotFoundExceptionMessage() {
        String errorMessage = "No addresses found for user";
        AddressNotFoundException exception = assertThrows(AddressNotFoundException.class, () -> {
            throw new AddressNotFoundException(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testAddressNotFoundExceptionInheritance() {
        AddressNotFoundException exception = new AddressNotFoundException("Test message");
        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}

