package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.GetOwnerRestaurantsInDTO;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;
import java.util.Objects;

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
     * Add restaurant.
     * @param restaurantInDTO
     * @param image
     * @return String message
     */
    @Override
    public String save(RestaurantInDTO restaurantInDTO, MultipartFile image) {
        UserOutDTO user = userClient.getUserById(restaurantInDTO.getOwnerId()).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() == Role.USER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_ADD_RESTAURANT);
        }
        Restaurant restaurantAlreadyExists = restaurantRepository.findByEmail(restaurantInDTO.getEmail());
        if (restaurantAlreadyExists != null) {
            throw new EmailAlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS);
        }

        Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);
        try {
            if (image != null && !image.isEmpty()) {
                restaurant.setImage(image.getBytes());
            }
            restaurantRepository.save(restaurant);
            return Constants.RESTAURANT_ADDED_SUCCESSFULLY;
        } catch (IOException e) {
            throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + e.getMessage());
        }
    }

    /**
     * Update Restaurant.
     * @param updateRestaurantInDTO
     * @return String message.
     */
    @Override
    public String updateRestaurant(UpdateRestaurantInDTO updateRestaurantInDTO) {
        UserOutDTO user = userClient.getUserById(updateRestaurantInDTO.getLoggedInOwnerId()).getBody();
        if (user == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        if (user.getRole() == Role.USER) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_RESTAURANT);
        }
        Restaurant restaurant = restaurantRepository.findById(updateRestaurantInDTO.getRestaurantId());
        if (restaurant == null) {
           throw new RestaurantsNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        if (restaurant.getOwnerId() != updateRestaurantInDTO.getLoggedInOwnerId()) {
            throw new UserNotValidException(Constants.YOU_CANNOT_UPDATE_RESTAURANT);
        }
        if (!Objects.equals(restaurant.getEmail(), updateRestaurantInDTO.getEmail())) {
            Restaurant restaurantAlreadyExists = restaurantRepository.findByEmail(updateRestaurantInDTO.getEmail());
            if (restaurantAlreadyExists != null) {
                throw new EmailAlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS);
            }
        }
        restaurant.setName(updateRestaurantInDTO.getName());
        restaurant.setEmail(updateRestaurantInDTO.getEmail());
        restaurant.setPhone(updateRestaurantInDTO.getPhone());
        restaurant.setAddress(updateRestaurantInDTO.getAddress());
        MultipartFile image = updateRestaurantInDTO.getImage();
        try {
            if (image != null && !image.isEmpty()) {
                restaurant.setImage(image.getBytes());
            }
            restaurantRepository.save(restaurant);
            return Constants.RESTAURANT_UPDATED_SUCCESSFULLY;
        } catch (IOException e) {
            throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + e.getMessage());
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
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurants;
    }

    /**
     * Get Restaurants by owner ID.
     * @param getOwnerRestaurantsInDTO
     * @return restaurants list
     */
    @Override
    public List<Restaurant> findByOwnerId(GetOwnerRestaurantsInDTO getOwnerRestaurantsInDTO) {
        List<Restaurant> restaurant = restaurantRepository.findByOwnerId(getOwnerRestaurantsInDTO.getOwnerId());
        if (restaurant.isEmpty()) {
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurant;
    }

    /**
     * Get Restaurant By Id.
     * @param restaurantId
     * @return
     */
    @Override
    public Restaurant findById(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantsNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurant;
    }
}
