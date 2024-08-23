package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /**
     * Get all restaurants.
     * @return all restaurants
     */
    List<Restaurant> findAll();

    /**
     * Get restaurant by name.
     * @param name for filtering.
     * @return restaurant with the given name.
     */
    Restaurant findByName(String name);

    /**
     * Get restaurant by Id.
     * @param restaurantId for filtering.
     * @return retaurant with specific restaurantId
     */
    Restaurant findById(long restaurantId);
}
