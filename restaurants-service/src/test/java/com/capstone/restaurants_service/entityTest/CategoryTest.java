package com.capstone.restaurants_service.entityTest;

import com.capstone.restaurants_service.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testGetterAndSetter() {
        Category category = new Category();

        assertEquals(0, category.getCategoryId());
        category.setCategoryId(1);
        assertEquals(1, category.getCategoryId());

        assertEquals(0, category.getRestaurantId());
        category.setRestaurantId(2);
        assertEquals(2, category.getRestaurantId());

        assertNull(category.getName());
        category.setName("Category Name");
        assertEquals("Category Name", category.getName());
    }

    @Test
    public void testToString() {
        Category category = new Category();

        category.setCategoryId(1);
        category.setRestaurantId(2);
        category.setName("Category Name");

        assertEquals("Category(categoryId=1, restaurantId=2, name=Category Name)", category.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        Category category1 = buildCategory(1, 2, "Category Name");

        assertEquals(category1, category1);
        assertEquals(category1.hashCode(), category1.hashCode());

        assertNotEquals(category1, new Object());

        Category category2 = buildCategory(1, 2, "Category Name");
        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());

        category2 = buildCategory(2, 2, "Category Name");
        assertNotEquals(category1, category2);
        assertNotEquals(category1.hashCode(), category2.hashCode());

        category2 = buildCategory(1, 3, "Category Name");
        assertNotEquals(category1, category2);
        assertNotEquals(category1.hashCode(), category2.hashCode());

        category2 = buildCategory(1, 2, "Different Name");
        assertNotEquals(category1, category2);
        assertNotEquals(category1.hashCode(), category2.hashCode());

        Category category3 = new Category();
        Category category4 = new Category();
        assertEquals(category3, category4);
        assertEquals(category3.hashCode(), category4.hashCode());
    }

    private Category buildCategory(long categoryId, long restaurantId, String name) {
        Category category = new Category();

        category.setCategoryId(categoryId);
        category.setRestaurantId(restaurantId);
        category.setName(name);

        return category;
    }
}