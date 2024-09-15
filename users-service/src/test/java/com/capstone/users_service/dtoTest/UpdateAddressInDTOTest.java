package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.UpdateAddressInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateAddressInDTOTest {

    @Test
    public void testGetterAndSetter() {
        UpdateAddressInDTO updateAddressInDTO = new UpdateAddressInDTO();

        assertEquals(0, updateAddressInDTO.getUserId());
        long userId = 1L;
        updateAddressInDTO.setUserId(userId);
        assertEquals(userId, updateAddressInDTO.getUserId());

        assertEquals(0, updateAddressInDTO.getAddressId());
        long addressId = 1L;
        updateAddressInDTO.setAddressId(addressId);
        assertEquals(addressId, updateAddressInDTO.getAddressId());

        assertNull(updateAddressInDTO.getAddress());
        String address = "123 Main St";
        updateAddressInDTO.setAddress(address);
        assertEquals(address, updateAddressInDTO.getAddress());

        assertNull(updateAddressInDTO.getCity());
        String city = "New York";
        updateAddressInDTO.setCity(city);
        assertEquals(city, updateAddressInDTO.getCity());

        assertEquals(0, updateAddressInDTO.getPincode());
        long pincode = 123456;
        updateAddressInDTO.setPincode(pincode);
        assertEquals(pincode, updateAddressInDTO.getPincode());

        assertNull(updateAddressInDTO.getState());
        String state = "NY";
        updateAddressInDTO.setState(state);
        assertEquals(state, updateAddressInDTO.getState());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 1L;
        long addressId = 1L;
        String address = "123 Main St";
        String city = "New York";
        long pincode = 123456;
        String state = "NY";

        UpdateAddressInDTO dto1 = buildUpdateAddressInDTO(userId, addressId, address, city, pincode, state);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        UpdateAddressInDTO dto2 = buildUpdateAddressInDTO(userId, addressId, address, city, pincode, state);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId + 1, addressId, address, city, pincode, state);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId, addressId + 1, address, city, pincode, state);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId, addressId, address + " Apt 4", city, pincode, state);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId, addressId, address, city + " Village", pincode, state);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId, addressId, address, city, pincode + 1, state);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUpdateAddressInDTO(userId, addressId, address, city, pincode, state + " State");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new UpdateAddressInDTO();
        dto2 = new UpdateAddressInDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        UpdateAddressInDTO dto = new UpdateAddressInDTO();
        dto.setUserId(1L);
        dto.setAddressId(1L);
        dto.setAddress("123 Main St");
        dto.setCity("New York");
        dto.setPincode(123456);
        dto.setState("NY");

        assertEquals("UpdateAddressInDTO(userId=1, addressId=1, address=123 Main St, city=New York, pincode=123456, state=NY)", dto.toString());
    }

    private UpdateAddressInDTO buildUpdateAddressInDTO(long userId, long addressId, String address, String city, long pincode, String state) {
        UpdateAddressInDTO dto = new UpdateAddressInDTO();
        dto.setUserId(userId);
        dto.setAddressId(addressId);
        dto.setAddress(address);
        dto.setCity(city);
        dto.setPincode(pincode);
        dto.setState(state);
        return dto;
    }
}