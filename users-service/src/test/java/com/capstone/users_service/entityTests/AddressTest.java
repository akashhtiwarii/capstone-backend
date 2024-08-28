package com.capstone.users_service.entityTests;

import com.capstone.users_service.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    private Address address1;
    private Address address2;

    @BeforeEach
    public void setUp() {
        address1 = new Address(1L, 1L, "Smriti Nagar", 491001L, "Bhilai", "Chhattisgarh");
        address2 = new Address(1L, 1L, "Smriti Nagar", 491001L, "Bhilai", "Chhattisgarh");
    }

    @Test
    public void testHashCode() {
        assertEquals(address1.hashCode(), address2.hashCode());
        address2.setUserId(2L);
        assertNotEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {

        assertEquals(1L, address1.getAddressId());
        assertEquals(1L, address1.getUserId());
        assertEquals("Smriti Nagar", address1.getAddress());
        assertEquals(491001L, address1.getPincode());
        assertEquals("Bhilai", address1.getCity());
        assertEquals("Chhattisgarh", address1.getState());

        address1.setAddressId(2L);
        assertEquals(2L, address1.getAddressId());

        address1.setUserId(2L);
        assertEquals(2L, address1.getUserId());

        address1.setAddress("Supela");
        assertEquals("Supela", address1.getAddress());

        address1.setPincode(497001L);
        assertEquals(497001L, address1.getPincode());

        address1.setCity("Ambikapur");
        assertEquals("Ambikapur", address1.getCity());

        address1.setState("Madhya Pradesh");
        assertEquals("Madhya Pradesh", address1.getState());

    }

    @Test
    public void testEquals() {
        Address address1 = new Address(1L, 1001L, "ABC Street", 123456, "ABC CITY", "CG");
        Address address2 = new Address(1L, 1001L, "ABC Street", 123456, "ABC CITY", "CG");
        Address address3 = new Address(2L, 1001L, "ABC Street", 123456, "ABC CITY", "CG");
        Address address4 = new Address(1L, 1002L, "ABC Street", 123456, "ABC CITY", "CG");
        Address address5 = new Address(1L, 1001L, "456 Elm St", 123456, "ABC CITY", "CG");
        Address address6 = new Address(1L, 1001L, "ABC Street", 654321, "ABC CITY", "CG");
        Address address7 = new Address(1L, 1001L, "ABC Street", 123456, "XYZ City", "CG");
        assertTrue(address1.equals(address1));
        assertFalse(address1.equals(address3));
        assertFalse(address1.equals(address4));
        assertFalse(address1.equals(address5));
        assertFalse(address1.equals(address6));
        assertFalse(address1.equals(address7));
        assertTrue(address1.equals(address2));
    }
}
