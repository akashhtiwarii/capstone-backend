package com.capstone.orders_service.dtoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.dto.AddressOutDTO;
import org.junit.jupiter.api.Test;

public class AddressOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        AddressOutDTO addressOutDTO = new AddressOutDTO();

        assertEquals(0, addressOutDTO.getAddressId());
        long addressId = 1L;
        addressOutDTO.setAddressId(addressId);
        assertEquals(addressId, addressOutDTO.getAddressId());

        assertEquals(0, addressOutDTO.getUserId());
        long userId = 2L;
        addressOutDTO.setUserId(userId);
        assertEquals(userId, addressOutDTO.getUserId());

        assertNull(addressOutDTO.getAddress());
        String address = "address";
        addressOutDTO.setAddress(address);
        assertEquals(address, addressOutDTO.getAddress());

        assertNull(addressOutDTO.getPincode());
        String pincode = "12345";
        addressOutDTO.setPincode(pincode);
        assertEquals(pincode, addressOutDTO.getPincode());

        assertNull(addressOutDTO.getCity());
        String city = "Anytown";
        addressOutDTO.setCity(city);
        assertEquals(city, addressOutDTO.getCity());

        assertNull(addressOutDTO.getState());
        String state = "state";
        addressOutDTO.setState(state);
        assertEquals(state, addressOutDTO.getState());
    }

    @Test
    public void testToString() {
        AddressOutDTO addressOutDTO = new AddressOutDTO();

        long addressId = 1L;
        addressOutDTO.setAddressId(addressId);

        long userId = 2L;
        addressOutDTO.setUserId(userId);

        String address = "address";
        addressOutDTO.setAddress(address);

        String pincode = "12345";
        addressOutDTO.setPincode(pincode);

        String city = "Anytown";
        addressOutDTO.setCity(city);

        String state = "state";
        addressOutDTO.setState(state);

        assertEquals("address, Anytown, state, 12345", addressOutDTO.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        long addressId = 1L;
        long userId = 2L;
        String address = "address";
        String pincode = "12345";
        String city = "Anytown";
        String state = "state";

        AddressOutDTO addressOutDTO1 = buildAddressOutDTO(addressId, userId, address, pincode, city, state);

        assertEquals(addressOutDTO1, addressOutDTO1);
        assertEquals(addressOutDTO1.hashCode(), addressOutDTO1.hashCode());

        assertNotEquals(addressOutDTO1, new Object());

        AddressOutDTO addressOutDTO2 = buildAddressOutDTO(addressId, userId, address, pincode, city, state);
        assertEquals(addressOutDTO1, addressOutDTO2);
        assertEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId + 1, userId, address, pincode, city, state);
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId, userId + 1, address, pincode, city, state);
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId, userId, address + " address2", pincode, city, state);
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId, userId, address, pincode + "1", city, state);
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId, userId, address, pincode, city + "city2", state);
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO2 = buildAddressOutDTO(addressId, userId, address, pincode, city, state + "state2");
        assertNotEquals(addressOutDTO1, addressOutDTO2);
        assertNotEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());

        addressOutDTO1 = new AddressOutDTO();
        addressOutDTO2 = new AddressOutDTO();
        assertEquals(addressOutDTO1, addressOutDTO2);
        assertEquals(addressOutDTO1.hashCode(), addressOutDTO2.hashCode());
    }

    private AddressOutDTO buildAddressOutDTO(long addressId, long userId, String address, String pincode, String city, String state) {
        AddressOutDTO addressOutDTO = new AddressOutDTO();

        addressOutDTO.setAddressId(addressId);
        addressOutDTO.setUserId(userId);
        addressOutDTO.setAddress(address);
        addressOutDTO.setPincode(pincode);
        addressOutDTO.setCity(city);
        addressOutDTO.setState(state);

        return addressOutDTO;
    }
}
