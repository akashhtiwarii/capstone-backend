package com.capstone.orders_service.dtoTest;

import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.OrderDetailOutDTO;
import com.capstone.orders_service.dto.RestaurantOrderDetailsOutDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantOrderDetailsOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        RestaurantOrderDetailsOutDTO restaurantOrderDetailsOutDTO = new RestaurantOrderDetailsOutDTO();

        assertEquals(0, restaurantOrderDetailsOutDTO.getUserId());
        long userId = 12345L;
        restaurantOrderDetailsOutDTO.setUserId(userId);
        assertEquals(userId, restaurantOrderDetailsOutDTO.getUserId());

        assertNull(restaurantOrderDetailsOutDTO.getUserName());
        String userName = "name";
        restaurantOrderDetailsOutDTO.setUserName(userName);
        assertEquals(userName, restaurantOrderDetailsOutDTO.getUserName());

        assertEquals(0, restaurantOrderDetailsOutDTO.getOrderId());
        long orderId = 67890L;
        restaurantOrderDetailsOutDTO.setOrderId(orderId);
        assertEquals(orderId, restaurantOrderDetailsOutDTO.getOrderId());

        assertNull(restaurantOrderDetailsOutDTO.getOrderDetailOutDTOS());
        List<OrderDetailOutDTO> orderDetails = new ArrayList<>();
        restaurantOrderDetailsOutDTO.setOrderDetailOutDTOS(orderDetails);
        assertEquals(orderDetails, restaurantOrderDetailsOutDTO.getOrderDetailOutDTOS());

        assertNull(restaurantOrderDetailsOutDTO.getStatus());
        Status status = Status.PENDING;
        restaurantOrderDetailsOutDTO.setStatus(status);
        assertEquals(status, restaurantOrderDetailsOutDTO.getStatus());

        assertNull(restaurantOrderDetailsOutDTO.getAddress());
        String address = "address";
        restaurantOrderDetailsOutDTO.setAddress(address);
        assertEquals(address, restaurantOrderDetailsOutDTO.getAddress());
    }

    @Test
    public void testEqualsAndHashcode() {
        long userId = 12345L;
        String userName = "name";
        long orderId = 67890L;
        List<OrderDetailOutDTO> orderDetails = new ArrayList<>();
        Status status = Status.PENDING;
        String address = "address";

        RestaurantOrderDetailsOutDTO dto1 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId, orderDetails, status, address);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        RestaurantOrderDetailsOutDTO dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId, orderDetails, status, address);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId + 1, userName, orderId, orderDetails, status, address);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName + " name2", orderId, orderDetails, status, address);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId + 1, orderDetails, status, address);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId, null, status, address);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId, orderDetails, Status.COMPLETED, address);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildRestaurantOrderDetailsOutDTO(userId, userName, orderId, orderDetails, status, "address2");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new RestaurantOrderDetailsOutDTO();
        dto2 = new RestaurantOrderDetailsOutDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    private RestaurantOrderDetailsOutDTO buildRestaurantOrderDetailsOutDTO(long userId, String userName, long orderId, List<OrderDetailOutDTO> orderDetails, Status status, String address) {
        RestaurantOrderDetailsOutDTO dto = new RestaurantOrderDetailsOutDTO();

        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setOrderId(orderId);
        dto.setOrderDetailOutDTOS(orderDetails);
        dto.setStatus(status);
        dto.setAddress(address);

        return dto;
    }
}

