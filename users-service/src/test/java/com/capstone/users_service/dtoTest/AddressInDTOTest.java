package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.AddressInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressInDTOTest {

    @Test
    public void testGetterAndSetter() {
        AddressInDTO addressInDTO = new AddressInDTO();

        assertEquals(0, addressInDTO.getUserId());
        long userId = 123;
        addressInDTO.setUserId(userId);
        assertEquals(userId, addressInDTO.getUserId());

        assertNull(addressInDTO.getAddress());
        String address = "address";
        addressInDTO.setAddress(address);
        assertEquals(address, addressInDTO.getAddress());

        assertEquals(0, addressInDTO.getPincode());
        long pincode = 123456;
        addressInDTO.setPincode(pincode);
        assertEquals(pincode, addressInDTO.getPincode());

        assertNull(addressInDTO.getCity());
        String city = "Test City";
        addressInDTO.setCity(city);
        assertEquals(city, addressInDTO.getCity());

        assertNull(addressInDTO.getState());
        String state = "Test State";
        addressInDTO.setState(state);
        assertEquals(state, addressInDTO.getState());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 123;
        String address = "address";
        long pincode = 123456;
        String city = "Test City";
        String state = "Test State";

        AddressInDTO addressInDTO1 = buildAddressInDTO(userId, address, pincode, city, state);

        assertEquals(addressInDTO1, addressInDTO1);
        assertEquals(addressInDTO1.hashCode(), addressInDTO1.hashCode());

        assertNotEquals(addressInDTO1, new Object());

        AddressInDTO addressInDTO2 = buildAddressInDTO(userId, address, pincode, city, state);
        assertEquals(addressInDTO1, addressInDTO2);
        assertEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());

        addressInDTO2 = buildAddressInDTO(userId + 1, address, pincode, city, state);
        assertNotEquals(addressInDTO1, addressInDTO2);
        assertNotEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());

        addressInDTO2 = buildAddressInDTO(userId, address + " Apt 4", pincode, city, state);
        assertNotEquals(addressInDTO1, addressInDTO2);
        assertNotEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());

        addressInDTO2 = buildAddressInDTO(userId, address, pincode + 1, city, state);
        assertNotEquals(addressInDTO1, addressInDTO2);
        assertNotEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());

        addressInDTO2 = buildAddressInDTO(userId, address, pincode, city + " East", state);
        assertNotEquals(addressInDTO1, addressInDTO2);
        assertNotEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());

        addressInDTO2 = buildAddressInDTO(userId, address, pincode, city, state + " North");
        assertNotEquals(addressInDTO1, addressInDTO2);
        assertNotEquals(addressInDTO1.hashCode(), addressInDTO2.hashCode());
    }

    @Test
    public void testToString() {
        AddressInDTO addressInDTO = new AddressInDTO();

        long userId = 123;
        String address = "address";
        long pincode = 123456;
        String city = "Test City";
        String state = "Test State";

        addressInDTO.setUserId(userId);
        addressInDTO.setAddress(address);
        addressInDTO.setPincode(pincode);
        addressInDTO.setCity(city);
        addressInDTO.setState(state);

        assertEquals("AddressInDTO(userId=123, address=address, pincode=123456, city=Test City, state=Test State)",
                addressInDTO.toString());
    }

    private AddressInDTO buildAddressInDTO(long userId, String address, long pincode, String city, String state) {
        AddressInDTO addressInDTO = new AddressInDTO();
        addressInDTO.setUserId(userId);
        addressInDTO.setAddress(address);
        addressInDTO.setPincode(pincode);
        addressInDTO.setCity(city);
        addressInDTO.setState(state);
        return addressInDTO;
    }
}