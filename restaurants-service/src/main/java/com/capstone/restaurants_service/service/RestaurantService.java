package com.capstone.restaurants_service.service;

import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing {@link Restaurant} entities.
 * <p>
 * This interface defines methods for performing CRUD operations and other restaurant-related
 * actions such as adding, updating, retrieving, and deleting restaurants in the system.
 * </p>
 */
public interface RestaurantService {

    /**
     * Adds a new restaurant to the system.
     * <p>
     * This method accepts a {@link RestaurantInDTO} object containing the details of the restaurant
     * to be added, along with an optional image file. It returns a confirmation message upon
     * successful addition of the restaurant.
     * </p>
     * @param restaurantInDTO the data transfer object containing the details of the restaurant to be added
     * @param image an optional image file associated with the restaurant
     * @return a confirmation message indicating the result of the addition operation
     */
    String save(RestaurantInDTO restaurantInDTO, MultipartFile image);

    /**
     * Retrieves all restaurants in the system.
     * <p>
     * This method returns a list of {@link Restaurant} entities representing all the restaurants
     * present in the system.
     * </p>
     * @return a {@link List} of all {@link Restaurant} entities
     */
    List<Restaurant> findAll();

    /**
     * Retrieves all restaurants owned by a specific owner.
     * <p>
     * This method returns a list of {@link Restaurant} entities that are associated with the
     * specified owner identified by the provided owner ID.
     * </p>
     * @param ownerId the ID of the owner whose restaurants are to be retrieved
     * @return a {@link List} of {@link Restaurant} entities associated with the given owner ID
     */
    List<Restaurant> findByOwnerId(long ownerId);

    /**
     * Retrieves a restaurant by its ID.
     * <p>
     * This method returns a {@link Restaurant} entity identified by the provided restaurant ID.
     * </p>
     * @param restaurantId the ID of the restaurant to be retrieved
     * @return the {@link Restaurant} entity associated with the given restaurant ID
     */
    Restaurant findById(long restaurantId);

    /**
     * Updates an existing restaurant.
     * <p>
     * This method updates the details of an existing restaurant using the information in the
     * {@link UpdateRestaurantInDTO} object. An optional image file can also be associated
     * with the updated restaurant.
     * </p>
     * @param updateRestaurantInDTO the data transfer object containing the updated details of the restaurant
     * @param image an optional image file associated with the updated restaurant
     * @return a confirmation message indicating the result of the update operation
     */
    String updateRestaurant(UpdateRestaurantInDTO updateRestaurantInDTO, MultipartFile image);
}
