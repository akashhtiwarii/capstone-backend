package com.capstone.users_service.entityTests;

import com.capstone.users_service.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    @Test
    public void testGetterAndSetter() {
        Address address = new Address();

        assertEquals(0, address.getAddressId());
        long addressId = 1L;
        address.setAddressId(addressId);
        assertEquals(addressId, address.getAddressId());

        assertEquals(0, address.getUserId());
        long userId = 101L;
        address.setUserId(userId);
        assertEquals(userId, address.getUserId());

        assertNull(address.getAddress());
        String addressLine = "address";
        address.setAddress(addressLine);
        assertEquals(addressLine, address.getAddress());

        assertEquals(0, address.getPincode());
        long pincode = 123456;
        address.setPincode(pincode);
        assertEquals(pincode, address.getPincode());

        assertNull(address.getCity());
        String city = "city";
        address.setCity(city);
        assertEquals(city, address.getCity());

        assertNull(address.getState());
        String state = "state";
        address.setState(state);
        assertEquals(state, address.getState());
    }

    @Test
    public void testEqualsAndHashCode() {
        Address address1 = buildAddress(1L, 101L, "address", 123456, "city", "state");
        Address address2 = buildAddress(1L, 101L, "address", 123456, "city", "state");

        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());

        address2.setAddressId(2L);
        assertNotEquals(address1, address2);
        assertNotEquals(address1.hashCode(), address2.hashCode());

        address2 = buildAddress(1L, 102L, "address", 123456, "city", "state");
        assertNotEquals(address1, address2);

        address2 = buildAddress(1L, 101L, "456 Broadway", 123456, "city", "state");
        assertNotEquals(address1, address2);

        address2 = buildAddress(1L, 101L, "address", 654321, "city", "state");
        assertNotEquals(address1, address2);

        address2 = buildAddress(1L, 101L, "address", 123456, "city2", "state");
        assertNotEquals(address1, address2);

        address2 = buildAddress(1L, 101L, "address", 123456, "city", "state2");
        assertNotEquals(address1, address2);

        Address address3 = new Address();
        Address address4 = new Address();
        assertEquals(address3, address4);
        assertEquals(address3.hashCode(), address4.hashCode());
    }

    @Test
    public void testToString() {
        Address address = buildAddress(1L, 101L, "address", 123456, "city", "state");
        String expectedString = "address, city, state, 123456";
        assertEquals(expectedString, address.toString());
    }

    private Address buildAddress(long addressId, long userId, String address, long pincode, String city, String state) {
        return new Address(addressId, userId, address, pincode, city, state);
    }
}