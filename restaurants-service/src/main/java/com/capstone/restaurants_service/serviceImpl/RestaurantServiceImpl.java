package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.OutDTO.UserOutDTO;
import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.exceptions.EmailAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.RestaurantsNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotFoundException;
import com.capstone.restaurants_service.exceptions.UserNotValidException;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.RestaurantService;
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
     * User client for communicating with user service.
     */
    @Autowired
    private UserClient userClient;
    /**
     * Add a new Restaurant.
     * @param restaurantInDTO request object
     * @return message after saving restaurant to database
     */
    @Override
    public String save(RestaurantInDTO restaurantInDTO) {
        try {
            UserOutDTO user = userClient.getUserById(restaurantInDTO.getOwnerId()).getBody();
            if (user == null) {
                throw new UserNotFoundException("Owner not in database");
            }
            if (user.getRole() == Role.USER) {
                throw new UserNotValidException("You cannot add a restaurant");
            }
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
        Restaurant restaurantAlreadyExists = restaurantRepository.findByEmail(restaurantInDTO.getEmail());
        if (restaurantAlreadyExists != null) {
            throw new EmailAlreadyExistsException("Email ID already Exists");
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
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            if (restaurants.isEmpty()) {
                throw new RestaurantsNotFoundException("No Restaurants in the database");
            }
            return restaurants;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Get Restaurant By Id.
     * @param restaurantId
     * @return
     */
    @Override
    public Restaurant findById(long restaurantId) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId);
            if (restaurant == null) {
                throw new RestaurantsNotFoundException("No Restaurants in the database");
            }
            return restaurant;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
}

