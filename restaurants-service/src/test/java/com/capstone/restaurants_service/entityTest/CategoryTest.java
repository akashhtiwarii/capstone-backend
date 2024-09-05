package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testGettersAndSetters() {
        Category category = new Category();
        category.setCategoryId(5L);
        category.setRestaurantId(10L);
        category.setName("Desserts");

        assertEquals(5L, category.getCategoryId());
        assertEquals(10L, category.getRestaurantId());
        assertEquals("Desserts", category.getName());
    }

    @Test
    void testEqualsMethod() {
        Category category1 = new Category(1L, 2L, "Italian");
        Category category2 = new Category(1L, 2L, "Italian");
        Category category3 = new Category(2L, 3L, "French");

        assertEquals(category1, category2);
        assertNotEquals(category1, category3);
    }

    @Test
    void testEqualsMethodWithDifferentObjects() {
        Category category = new Category(1L, 2L, "Mexican");

        assertNotEquals(null, category);
        assertNotEquals(category, new Object());
    }

    @Test
    void testHashCodeMethod() {
        Category category1 = new Category(1L, 2L, "Chinese");
        Category category2 = new Category(1L, 2L, "Chinese");
        Category category3 = new Category(2L, 3L, "Japanese");

        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1.hashCode(), category3.hashCode());
    }
}
