package com.capstone.orders_service.dtoTest;

import static org.junit.jupiter.api.Assertions.*;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.UserFoodItemOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserOrderDetailsOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO();

        assertNull(userOrderDetailsOutDTO.getRestaurantName());
        String restaurantName = "Test Restaurant";
        userOrderDetailsOutDTO.setRestaurantName(restaurantName);
        assertEquals(restaurantName, userOrderDetailsOutDTO.getRestaurantName());

        assertNull(userOrderDetailsOutDTO.getFoodItemOutDTOS());
        List<UserFoodItemOutDTO> foodItemOutDTOS = Collections.singletonList(new UserFoodItemOutDTO());
        userOrderDetailsOutDTO.setFoodItemOutDTOS(foodItemOutDTOS);
        assertEquals(foodItemOutDTOS, userOrderDetailsOutDTO.getFoodItemOutDTOS());

        assertNull(userOrderDetailsOutDTO.getStatus());
        Status status = Status.PENDING;
        userOrderDetailsOutDTO.setStatus(status);
        assertEquals(status, userOrderDetailsOutDTO.getStatus());

        assertNull(userOrderDetailsOutDTO.getOrderTime());
        LocalDateTime orderTime = LocalDateTime.now();
        userOrderDetailsOutDTO.setOrderTime(orderTime);
        assertEquals(orderTime, userOrderDetailsOutDTO.getOrderTime());
    }

    @Test
    public void testEqualsAndHashcode() {
        String restaurantName = "Test Restaurant";
        List<UserFoodItemOutDTO> foodItemOutDTOS = Collections.singletonList(new UserFoodItemOutDTO());
        Status status = Status.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();

        UserOrderDetailsOutDTO dto1 = buildUserOrderDetailsOutDTO(restaurantName, foodItemOutDTOS, status, orderTime);

        assertEquals(dto1, dto1);
        assertEquals(dto1.hashCode(), dto1.hashCode());

        assertNotEquals(dto1, new Object());

        UserOrderDetailsOutDTO dto2 = buildUserOrderDetailsOutDTO(restaurantName, foodItemOutDTOS, status, orderTime);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserOrderDetailsOutDTO(restaurantName + " Updated", foodItemOutDTOS, status, orderTime);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserOrderDetailsOutDTO(restaurantName, Arrays.asList(new UserFoodItemOutDTO()), status, orderTime);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserOrderDetailsOutDTO(restaurantName, foodItemOutDTOS, Status.COMPLETED, orderTime);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2 = buildUserOrderDetailsOutDTO(restaurantName, foodItemOutDTOS, status, LocalDateTime.now().minusDays(1));
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto1 = new UserOrderDetailsOutDTO();
        dto2 = new UserOrderDetailsOutDTO();
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    private UserOrderDetailsOutDTO buildUserOrderDetailsOutDTO(String restaurantName, List<UserFoodItemOutDTO> foodItemOutDTOS, Status status, LocalDateTime orderTime) {
        UserOrderDetailsOutDTO dto = new UserOrderDetailsOutDTO();

        dto.setRestaurantName(restaurantName);
        dto.setFoodItemOutDTOS(foodItemOutDTOS);
        dto.setStatus(status);
        dto.setOrderTime(orderTime);

        return dto;
    }
}
