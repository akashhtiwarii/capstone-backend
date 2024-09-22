package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.UserFoodItemOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserOrderDetailsOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO();

        assertNull(userOrderDetailsOutDTO.getRestaurantName());
        String restaurantName = "Restaurant A";
        userOrderDetailsOutDTO.setRestaurantName(restaurantName);
        assertEquals(restaurantName, userOrderDetailsOutDTO.getRestaurantName());

        assertNull(userOrderDetailsOutDTO.getRestaurantEmail());
        String restaurantEmail = "email@gmail.com";
        userOrderDetailsOutDTO.setRestaurantEmail(restaurantEmail);
        assertEquals(restaurantEmail, userOrderDetailsOutDTO.getRestaurantEmail());

        assertEquals(0, userOrderDetailsOutDTO.getOrderId());
        long orderId = 123L;
        userOrderDetailsOutDTO.setOrderId(orderId);
        assertEquals(orderId, userOrderDetailsOutDTO.getOrderId());

        assertNotNull(userOrderDetailsOutDTO.getFoodItemOutDTOS());
        List<UserFoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
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
    public void testConstructor() {
        String restaurantName = "Restaurant B";
        String restaurantEmail = "emailb@gmail.com";
        long orderId = 456L;
        List<UserFoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
        Status status = Status.COMPLETED;
        LocalDateTime orderTime = LocalDateTime.of(2023, 9, 17, 12, 30);

        UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO(
                restaurantName, restaurantEmail, orderId, foodItemOutDTOS, status, orderTime
        );

        assertEquals(restaurantName, userOrderDetailsOutDTO.getRestaurantName());
        assertEquals(restaurantEmail, userOrderDetailsOutDTO.getRestaurantEmail());
        assertEquals(orderId, userOrderDetailsOutDTO.getOrderId());
        assertEquals(foodItemOutDTOS, userOrderDetailsOutDTO.getFoodItemOutDTOS());
        assertEquals(status, userOrderDetailsOutDTO.getStatus());
        assertEquals(orderTime, userOrderDetailsOutDTO.getOrderTime());
    }

    @Test
    public void testEqualsAndHashcode() {
        String restaurantName = "Restaurant C";
        String restaurantEmail = "emailc@gmail.com";
        long orderId = 789L;
        List<UserFoodItemOutDTO> foodItemOutDTOS = new ArrayList<>();
        Status status = Status.PENDING;
        LocalDateTime orderTime = LocalDateTime.now();

        UserOrderDetailsOutDTO dto1 = new UserOrderDetailsOutDTO(
                restaurantName, restaurantEmail, orderId, foodItemOutDTOS, status, orderTime
        );
        UserOrderDetailsOutDTO dto2 = new UserOrderDetailsOutDTO(
                restaurantName, restaurantEmail, orderId, foodItemOutDTOS, status, orderTime
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setOrderId(999L);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testGetFoodItemOutDTOSImmutable() {
        List<UserFoodItemOutDTO> foodItems = new ArrayList<>();
        foodItems.add(new UserFoodItemOutDTO());

        UserOrderDetailsOutDTO userOrderDetailsOutDTO = new UserOrderDetailsOutDTO();
        userOrderDetailsOutDTO.setFoodItemOutDTOS(foodItems);

        List<UserFoodItemOutDTO> retrievedFoodItems = userOrderDetailsOutDTO.getFoodItemOutDTOS();
        assertEquals(1, retrievedFoodItems.size());

        assertThrows(UnsupportedOperationException.class, () -> {
            retrievedFoodItems.add(new UserFoodItemOutDTO());
        });
    }
}
