package com.capstone.restaurants_service.serviceImpl;

import com.capstone.restaurants_service.ENUM.Role;
import com.capstone.restaurants_service.exceptions.ResourceAlreadyExistsException;
import com.capstone.restaurants_service.exceptions.ResourceNotFoundException;
import com.capstone.restaurants_service.exceptions.ResourceNotValidException;
import com.capstone.restaurants_service.utils.Constants;
import com.capstone.restaurants_service.dto.RestaurantInDTO;
import com.capstone.restaurants_service.dto.UpdateRestaurantInDTO;
import com.capstone.restaurants_service.dto.UserOutDTO;
import com.capstone.restaurants_service.converters.RestaurantConverters;
import com.capstone.restaurants_service.entity.Restaurant;
import com.capstone.restaurants_service.feignClient.UserClient;
import com.capstone.restaurants_service.repository.RestaurantRepository;
import com.capstone.restaurants_service.service.RestaurantService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the {@link RestaurantService} interface.
 * <p>
 * This service handles the business logic related to restaurant operations,
 * including adding, updating, retrieving, and managing restaurants.
 * </p>
 */
@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    /**
     * Repository for managing {@link Restaurant} entities.
     */
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Client for communicating with the user service.
     */
    @Autowired
    private UserClient userClient;

    /**
     * Adds a new restaurant.
     * <p>
     * This method validates the user's role and ensures that the restaurant email
     * is unique before saving the restaurant to the database. It also handles image uploads.
     * </p>
     * @param restaurantInDTO the DTO containing the details of the restaurant to be added
     * @param image an optional image file associated with the restaurant
     * @return a message indicating the result of the operation
     * @throws ResourceNotFoundException if the user associated with the restaurant is not found
     * @throws ResourceNotValidException if the user role is not valid for adding a restaurant
     * @throws ResourceAlreadyExistsException if a restaurant with the given email already exists
     */
    @Override
    public String save(final RestaurantInDTO restaurantInDTO, final MultipartFile image) {
        try {
            UserOutDTO user = userClient.getUserById(restaurantInDTO.getOwnerId()).getBody();
            if (user == null) {
                log.error("User not found for ID: {}", restaurantInDTO.getOwnerId());
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() == Role.USER) {
                log.error("User with ID {} cannot add a restaurant", restaurantInDTO.getOwnerId());
                throw new ResourceNotValidException(Constants.YOU_CANNOT_ADD_RESTAURANT);
            }
            Restaurant restaurantAlreadyExists = restaurantRepository.findByEmail(restaurantInDTO.getEmail());
            if (restaurantAlreadyExists != null) {
                log.error("Restaurant with email {} already exists", restaurantInDTO.getEmail());
                throw new ResourceAlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS);
            }

            Restaurant restaurant = RestaurantConverters.restaurantInDTOTORestaurant(restaurantInDTO);
            try {
                if (image != null && !image.isEmpty()) {
                    String contentType = image.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        log.error("Invalid image type for file: {}", image.getOriginalFilename());
                        throw new ResourceNotValidException(Constants.INVALID_IMAGE_TYPE);
                    }
                    restaurant.setImage(image.getBytes());
                }
                restaurantRepository.save(restaurant);
                return Constants.RESTAURANT_ADDED_SUCCESSFULLY;
            } catch (ResourceNotValidException e) {
                log.error("Validation failed: {}", e.getMessage());
                throw e;
            } catch (IOException e) {
                log.error("Failed to upload image: {}", e.getMessage());
                throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error occurred: {}", e.getMessage());
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + e.getMessage());
            }
        } catch (FeignException e) {
            log.error("User service is down: {}", e.getMessage());
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Updates an existing restaurant.
     * <p>
     * This method validates the user's role and checks if the restaurant exists and if the
     * user has the rights to update it. It also ensures that the restaurant email is unique
     * before saving the updated details to the database.
     * </p>
     * @param updateRestaurantInDTO the DTO containing the updated details of the restaurant
     * @param image an optional image file associated with the restaurant
     * @return a message indicating the result of the operation
     * @throws ResourceNotFoundException if the user or the restaurant is not found
     * @throws ResourceNotValidException if the user role is not valid for updating the restaurant
     * @throws ResourceAlreadyExistsException if a restaurant with the given email already exists
     */
    @Override
    public String updateRestaurant(final UpdateRestaurantInDTO updateRestaurantInDTO, final MultipartFile image) {
        try {
            UserOutDTO user = userClient.getUserById(updateRestaurantInDTO.getLoggedInOwnerId()).getBody();
            if (user == null) {
                log.error("User not found for ID: {}", updateRestaurantInDTO.getLoggedInOwnerId());
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
            if (user.getRole() == Role.USER) {
                log.error("User with ID {} cannot update a restaurant", updateRestaurantInDTO.getLoggedInOwnerId());
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_RESTAURANT);
            }
            Restaurant restaurant = restaurantRepository.findById(updateRestaurantInDTO.getRestaurantId());
            if (restaurant == null) {
                log.error("Restaurant not found for ID: {}", updateRestaurantInDTO.getRestaurantId());
                throw new ResourceNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
            }
            if (restaurant.getOwnerId() != updateRestaurantInDTO.getLoggedInOwnerId()) {
                log.error(
                        "User with ID {} is not authorized to update restaurant ID: {}",
                        updateRestaurantInDTO.getLoggedInOwnerId(),
                        updateRestaurantInDTO.getRestaurantId()
                );
                throw new ResourceNotValidException(Constants.YOU_CANNOT_UPDATE_RESTAURANT);
            }
            if (!Objects.equals(restaurant.getEmail(), updateRestaurantInDTO.getEmail())) {
                Restaurant restaurantAlreadyExists = restaurantRepository.findByEmail(updateRestaurantInDTO.getEmail());
                if (restaurantAlreadyExists != null) {
                    log.error("Restaurant with email {} already exists", updateRestaurantInDTO.getEmail());
                    throw new ResourceAlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS);
                }
            }
            restaurant.setName(updateRestaurantInDTO.getName());
            restaurant.setEmail(updateRestaurantInDTO.getEmail());
            restaurant.setPhone(updateRestaurantInDTO.getPhone());
            restaurant.setAddress(updateRestaurantInDTO.getAddress());
            try {
                if (image != null && !image.isEmpty()) {
                    String contentType = image.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        log.error("Invalid image type for file: {}", image.getOriginalFilename());
                        throw new ResourceNotValidException(Constants.INVALID_IMAGE_TYPE);
                    }
                    restaurant.setImage(image.getBytes());
                }
                restaurantRepository.save(restaurant);
                return Constants.RESTAURANT_UPDATED_SUCCESSFULLY;
            } catch (ResourceNotValidException e) {
                log.error("Validation failed: {}", e.getMessage());
                throw e;
            } catch (IOException e) {
                log.error("Failed to upload image: {}", e.getMessage());
                throw new RuntimeException(Constants.FAILED_TO_UPLOAD_IMAGE + e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error occurred: {}", e.getMessage());
                throw new RuntimeException(Constants.UNEXPECTED_ERROR_OCCURRED + e.getMessage());
            }
        } catch (FeignException e) {
            log.error("User service is down: {}", e.getMessage());
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
    }

    /**
     * Retrieves all restaurants.
     * <p>
     * This method fetches the list of all restaurants from the database. If no restaurants are found,
     * an exception is thrown.
     * </p>
     * @return a list of {@link Restaurant} entities
     * @throws ResourceNotFoundException if no restaurants are found
     */
    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            log.error("No restaurants found");
            throw new ResourceNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurants;
    }

    /**
     * Retrieves restaurants by owner ID.
     * <p>
     * This method fetches all restaurants associated with the given owner ID. If no restaurants are found,
     * an exception is thrown.
     * </p>
     * @param ownerId the ID of the owner whose restaurants are to be retrieved
     * @return a list of {@link List} of Restaurants.
     */
    @Override
    public List<Restaurant> findByOwnerId(final long ownerId) {
        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(ownerId);
        if (restaurants.isEmpty()) {
            log.error("No restaurants found for owner ID: {}", ownerId);
            throw new ResourceNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurants;
    }

    /**
     * Retrieves a restaurant by its ID.
     * <p>
     * This method fetches a restaurant from the database using the provided restaurant ID. If the restaurant
     * is not found, an exception is thrown.
     * </p>
     * @param restaurantId the ID of the restaurant to retrieve
     * @return the {@link Restaurant} entity with the specified ID
     * @throws ResourceNotFoundException if the restaurant with the given ID does not exist
     */
    @Override
    public Restaurant findById(final long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant == null) {
            log.error("Restaurant not found for ID: {}", restaurantId);
            throw new ResourceNotFoundException(Constants.RESTAURANT_DOES_NOT_EXISTS);
        }
        return restaurant;
    }
}

