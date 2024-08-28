package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RestaurantRepository to connect with restaurants table.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
