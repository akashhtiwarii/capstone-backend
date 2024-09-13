package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link Restaurant} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations and
 * custom query methods for the {@link Restaurant} entity. It includes methods for retrieving
 * restaurants based on various criteria.
 * </p>
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Retrieves a {@link Restaurant} by its email address.
     * <p>
     * This method queries the database to find a restaurant that matches the provided email address.
     * </p>
     * @param email the email address of the restaurant to be retrieved
     * @return the {@link Restaurant} entity with the given email address,
     * or {@code null} if no matching restaurant is found
     */
    Restaurant findByEmail(String email);

    /**
     * Retrieves a {@link Restaurant} by its ID.
     * <p>
     * This method queries the database to find a restaurant that matches the provided ID.
     * </p>
     * @param restaurantId the ID of the restaurant to be retrieved
     * @return the {@link Restaurant} entity with the given ID, or {@code null} if no matching restaurant is found
     */
    Restaurant findById(long restaurantId);

    /**
     * Retrieves a list of all {@link Restaurant} entities.
     * <p>
     * This method queries the database to retrieve all restaurants.
     * </p>
     * @return a {@link List} of all {@link Restaurant} entities
     */
    List<Restaurant> findAll();

    /**
     * Retrieves a list of {@link Restaurant} entities associated with a specific owner.
     * <p>
     * This method queries the database to find all restaurants that belong to the provided owner ID.
     * </p>
     * @param ownerId the ID of the owner whose restaurants are to be retrieved
     * @return a {@link List} of {@link Restaurant} entities associated with the given owner ID
     */
    List<Restaurant> findByOwnerId(long ownerId);
}
