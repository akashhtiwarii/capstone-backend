package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link Category} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and custom query methods for the {@link Category} entity. It includes methods for
 * retrieving categories based on various criteria.
 * </p>
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieves a list of all categories associated with a specific restaurant.
     * <p>
     * This method queries the database to find all categories that are linked to the
     * restaurant identified by the provided restaurant ID.
     * </p>
     * @param restaurantId the ID of the restaurant whose categories are to be retrieved
     * @return a {@link List} of {@link Category} entities associated with the given restaurant ID
     */
    List<Category> findByRestaurantId(long restaurantId);

    /**
     * Retrieves a category by its name.
     * <p>
     * This method queries the database to find a category that matches the provided name.
     * </p>
     * @param name the name of the category to be retrieved
     * @return the {@link Category} entity that matches the given name, or {@code null} if not found
     */
    Category findByName(String name);

    /**
     * Retrieves a category by its name and associated restaurant ID.
     * <p>
     * This method queries the database to find a category that matches the provided name
     * and is associated with the given restaurant ID.
     * </p>
     * @param name the name of the category to be retrieved
     * @param restaurantId the ID of the restaurant associated with the category
     * @return the {@link Category} entity that matches the given name and restaurant ID, or {@code null} if not found
     */
    Category findByNameAndRestaurantId(String name, long restaurantId);

    /**
     * Retrieves a category by its ID.
     * <p>
     * This method queries the database to find a category that matches the provided ID.
     * </p>
     * @param categoryId the ID of the category to be retrieved
     * @return the {@link Category} entity that matches the given ID, or {@code null} if not found
     */
    Category findById(long categoryId);
}
