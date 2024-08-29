package com.capstone.restaurants_service.controller;

import com.capstone.restaurants_service.InDTO.RestaurantInDTO;
import com.capstone.restaurants_service.service.RestaurantService;
import com.capstone.restaurants_service.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RestaurantController {


    /**
     * Restaurant Service for accessing restaurants table operations.
     */
    @Autowired
    private RestaurantService restaurantService;
    /**
     * Logger for logging.
     */
    private final Logger logger = LogManager.getLogger(RestaurantController.class);

    /**
     * Add new restaurant.
     * @param restaurantInDTO request parameter
     * @return a string message
     */
    @PostMapping(Constants.USER_ADD_RESTAURANT_ENDPOINT)
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantInDTO restaurantInDTO) {
        logger.info("Adding new Restaurant : {}", restaurantInDTO.getName());
        String message = restaurantService.save(restaurantInDTO);
        logger.info("Added Restaurant : {}", restaurantInDTO.getName());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
