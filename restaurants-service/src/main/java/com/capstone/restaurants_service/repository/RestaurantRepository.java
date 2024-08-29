package com.capstone.restaurants_service.repository;
import com.capstone.restaurants_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RestaurantRepository to connect with restaurants table.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /**
     * Find restaurant by Email.
     * @param email restaurant email
     * @return restaurant
     */
    Restaurant findByEmail(String email);

    /**
     * Get a restaurant by ID.
     * @param restaurantId
     * @return Restaurant
     */
    Restaurant findById(long restaurantId);
}

