package com.capstone.users_service.serviceImpl;

import com.capstone.users_service.Enum.Role;
import com.capstone.users_service.InDTO.RestaurantInDTO;
import com.capstone.users_service.converters.RestaurantConverters;
import com.capstone.users_service.entity.Restaurant;
import com.capstone.users_service.entity.User;
import com.capstone.users_service.exceptions.RestaurantsNotFoundException;
import com.capstone.users_service.exceptions.UserNotFoundException;
import com.capstone.users_service.exceptions.UserNotValidException;
import com.capstone.users_service.repository.RestaurantRepository;
import com.capstone.users_service.repository.UserRepository;
import com.capstone.users_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RestaurantServiceImpl for implementing methods of RestaurantService.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    /**
     * restaurantRepository for connecting with address table in database.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * userRepository for connecting with address table in database.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Add a new Restaurant.
     * @param restaurantInDTO request object
     * @return message after saving restaurant to database
     */
    @Override
    public String save(RestaurantInDTO restaurantInDTO) {
        User user = userRepository.findById(restaurantInDTO.getOwnerId());
        if (user == null) {
            throw new UserNotFoundException("Owner not in database");
        }
        if (user.getRole() == Role.USER) {
            throw new UserNotValidException("You cannot add a restaurant");
        }
        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);
        try {
            restaurantRepository.save(restaurant);
            return "Restaurant added successfully";
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Get all restaurants.
     * @return the list of all restaurants
     */
    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
           throw new RestaurantsNotFoundException("No Restaurants in the database");
        }
        return restaurants;
    }
}
