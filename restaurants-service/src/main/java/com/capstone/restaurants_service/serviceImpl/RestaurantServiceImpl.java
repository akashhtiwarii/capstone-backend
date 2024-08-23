package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.RetaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RetaurantService {
    /**
     * restaurantRepository for executing jpa functions with restaurant table.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Get all restaurants.
     * @return all restaurants
     */
    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
    /**
     * Get Restaurant with a specific name.
     * @param name for filtering.
     * @return restaurant with a specific name
     */
    @Override
    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }
    /**
     * Get Restaurant with a specific restaurantId.
     * @param restaurantId for filtering.
     * @return restaurant with a specific restaurantId
     */
    @Override
    public Restaurant findById(long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }
    /**
     * Save a new restaurant to the database.
     * @param restaurant for adding
     * @return a string with a message
     */
    @Override
    public ResponseEntity<String> save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok("New Restaurant added successfully");
    }
}
