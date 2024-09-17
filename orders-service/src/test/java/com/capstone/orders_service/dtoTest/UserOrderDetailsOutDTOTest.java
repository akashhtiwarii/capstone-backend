package com.capstone.orders_service.dtoTest;

import com.capstone.orders_service.Enum.Status;
import com.capstone.orders_service.dto.UserFoodItemOutDTO;
import com.capstone.orders_service.dto.UserOrderDetailsOutDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserOrderDetailsOutDTOTest {

    @Test
    void testEqualsAndHashCode() {
        UserFoodItemOutDTO foodItem1 = new UserFoodItemOutDTO();
        foodItem1.setFoodId(1L);
        foodItem1.setName("Food");
        foodItem1.setQuantity(2);
        foodItem1.setPrice(20.0);

        UserFoodItemOutDTO foodItem2 = new UserFoodItemOutDTO();
        foodItem2.setFoodId(2L);
        foodItem2.setName("Food2");
        foodItem2.setQuantity(1);
        foodItem2.setPrice(10.0);

        List<UserFoodItemOutDTO> foodItems = Arrays.asList(foodItem1, foodItem2);

        UserOrderDetailsOutDTO order1 = new UserOrderDetailsOutDTO();
        order1.setRestaurantName("Best Restaurant");
        order1.setRestaurantEmail("contact@restaurant.com");
        order1.setOrderId(100L);
        order1.setFoodItemOutDTOS(foodItems);
        order1.setStatus(Status.PENDING);
        order1.setOrderTime(LocalDateTime.now());

        UserOrderDetailsOutDTO order2 = new UserOrderDetailsOutDTO();
        order2.setRestaurantName("Best Restaurant");
        order2.setRestaurantEmail("contact@restaurant.com");
        order2.setOrderId(100L);
        order2.setFoodItemOutDTOS(foodItems);
        order2.setStatus(Status.PENDING);
        order2.setOrderTime(order1.getOrderTime());

        UserOrderDetailsOutDTO order3 = new UserOrderDetailsOutDTO();
        order3.setRestaurantName("Another Restaurant");
        order3.setRestaurantEmail("contact@anotherrestaurant.com");
        order3.setOrderId(101L);
        order3.setFoodItemOutDTOS(foodItems);
        order3.setStatus(Status.COMPLETED);
        order3.setOrderTime(order1.getOrderTime().minusDays(1));

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1, order3);
        assertNotEquals(order1.hashCode(), order3.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        UserOrderDetailsOutDTO order = new UserOrderDetailsOutDTO();
        order.setRestaurantName("My Restaurant");
        order.setRestaurantEmail("email@gmail.com");
        order.setOrderId(1L);
        order.setStatus(Status.COMPLETED);
        order.setOrderTime(LocalDateTime.of(2024, 1, 1, 12, 0));

        UserFoodItemOutDTO foodItem1 = new UserFoodItemOutDTO();
        foodItem1.setFoodId(1L);
        foodItem1.setName("Food");
        foodItem1.setQuantity(2);
        foodItem1.setPrice(20.0);

        UserFoodItemOutDTO foodItem2 = new UserFoodItemOutDTO();
        foodItem2.setFoodId(2L);
        foodItem2.setName("Food2");
        foodItem2.setQuantity(1);
        foodItem2.setPrice(10.0);

        order.setFoodItemOutDTOS(Arrays.asList(foodItem1, foodItem2));

        assertEquals("My Restaurant", order.getRestaurantName());
        assertEquals("email@gmail.com", order.getRestaurantEmail());
        assertEquals(1L, order.getOrderId());
        assertEquals(Status.COMPLETED, order.getStatus());
        assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0), order.getOrderTime());
        assertEquals(2, order.getFoodItemOutDTOS().size());
        assertEquals("Food", order.getFoodItemOutDTOS().get(0).getName());
        assertEquals("Food2", order.getFoodItemOutDTOS().get(1).getName());
    }
}