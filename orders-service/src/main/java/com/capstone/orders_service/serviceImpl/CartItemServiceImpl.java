package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;
import com.capstone.orders_service.dto.CartItemsListOutDTO;
import com.capstone.orders_service.dto.FoodItemOutDTO;
import com.capstone.orders_service.dto.RestaurantOutDTO;
import com.capstone.orders_service.dto.UserOutDTO;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.ResourceNotFoundException;
import com.capstone.orders_service.exceptions.RestaurantConflictException;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.service.CartItemService;
import com.capstone.orders_service.utils.Constants;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Cart Items.
 * This class handles adding, updating, and deleting items from the cart,
 * as well as fetching cart details for a user.
 */
@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    /**
     * Repository for managing CartItem entities.
     */
    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Feign client for interacting with the User service.
     */
    @Autowired
    private UsersFeignClient usersFeignClient;

    /**
     * Feign client for interacting with the Restaurant service.
     */
    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    /**
     * Adds an item to the user's cart.
     *
     * @param addToCartInDTO DTO containing cart details to be added
     * @return A message indicating whether the item was added successfully
     * @throws ResourceNotFoundException if the user or food item is not found
     * @throws RestaurantConflictException if the user tries to add items from different restaurants
     */
    @Override
    public String addToCart(final AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);
        try {
            UserOutDTO user = usersFeignClient.getUserById(addToCartInDTO.getUserId()).getBody();
            if (user == null) {
                log.error("User Not Found : {}", addToCartInDTO.getUserId());
                throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            log.error("User Not Found : {}", addToCartInDTO.getUserId());
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        } catch (FeignException e) {
            log.error("User Service Down");
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
        try {
            FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
            if (foodItemOutDTO == null) {
                log.error("Food Not Found : {}", cartItem.getFoodId());
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
            cartItem.setPrice(cartItem.getQuantity() * foodItemOutDTO.getPrice());
        } catch (FeignException.NotFound e) {
            log.error("Food Not Found : {}", cartItem.getFoodId());
            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
        } catch (FeignException e) {
            log.error("Restaurant Service Down");
            throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
        }
        try {
            List<FoodItemOutDTO> foodItemOutDTOS = restaurantFeignClient.getFoodItemsByRestaurant(
                    addToCartInDTO.getRestaurantId()
            ).getBody();
            if (foodItemOutDTOS == null) {
                log.error("Food Items Not Found");
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
            if (foodItemOutDTOS.isEmpty()) {
                log.error("Food Items Not Found");
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
            List<Long> foodIds = foodItemOutDTOS.stream().map(FoodItemOutDTO::getFoodId).collect(Collectors.toList());
            if (!foodIds.contains(addToCartInDTO.getFoodId())) {
                log.error("Food Not Found : {}", addToCartInDTO.getFoodId());
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            log.error("Food Not Found : {}", cartItem.getFoodId());
            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
        } catch (FeignException e) {
            log.error("Restaurant Service Down");
            throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
        }
        List<CartItem> cartItemList = cartItemRepository.findByUserId(addToCartInDTO.getUserId());
        if (!cartItemList.isEmpty()) {
            if (addToCartInDTO.getRestaurantId() != cartItemList.get(0).getRestaurantId()) {
                log.error("Restaurant Conflict Exception");
                throw new RestaurantConflictException(Constants.RESTAURANT_CONFLICT);
            }
            CartItem cartItemItemAlreadyPresent = cartItemRepository.findByRestaurantIdAndUserIdAndFoodId(
                    addToCartInDTO.getRestaurantId(), addToCartInDTO.getUserId(), addToCartInDTO.getFoodId()
            );
            if (cartItemItemAlreadyPresent != null) {
                cartItemItemAlreadyPresent.setQuantity(cartItemItemAlreadyPresent.getQuantity() + 1);
                cartItemRepository.save(cartItemItemAlreadyPresent);
                return Constants.CART_ITEM_ADDED_SUCCESSFULLY;
            }
        }
        cartItemRepository.save(cartItem);
        return Constants.CART_ITEM_ADDED_SUCCESSFULLY;
    }

    /**
     * Deletes a specific item from the cart by its ID.
     *
     * @param cartItemId the ID of the cart item to be deleted
     * @return A message indicating the item was deleted
     * @throws ResourceNotFoundException if the cart item is not found
     */
    @Override
    public String deleteCartItem(final long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
            log.error("Cart Item Not Found : {}", cartItemId);
            throw new ResourceNotFoundException(Constants.CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
        return Constants.CART_ITEM_DELETED_SUCCESSFULLY;
    }

    /**
     * Retrieves all cart items for a specific user.
     *
     * @param userId the ID of the user whose cart items are to be retrieved
     * @return a list of CartItemOutDTO objects representing the user's cart items
     * @throws ResourceNotFoundException if no items are found in the cart
     */
    @Override
    public CartItemsListOutDTO getCartItems(final long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            log.error("Cart Items Not Found");
            throw new ResourceNotFoundException(Constants.CART_ITEM_NOT_FOUND);
        }
        List<CartItemOutDTO> cartItemOutDTOS = new ArrayList<>();
        double totalAmount = 0.0;
        for (CartItem cartItem: cartItems) {
            try {
                FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
                RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(
                        cartItem.getRestaurantId()
                ).getBody();
                if (foodItemOutDTO == null) {
                    log.error("Food Not Found : {}", cartItem.getFoodId());
                    throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
                }
                if (restaurant == null) {
                    log.error("Restaurant Not Found : {}", cartItem.getRestaurantId());
                    throw new ResourceNotFoundException(Constants.RESTAURANT_NOT_FOUND);
                }
                CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();
                cartItemOutDTO.setCartItemId(cartItem.getCartItemId());
                cartItemOutDTO.setUserId(cartItem.getUserId());
                cartItemOutDTO.setFoodName(foodItemOutDTO.getName());
                cartItemOutDTO.setRestaurantName(restaurant.getName());
                cartItemOutDTO.setQuantity(cartItem.getQuantity());
                cartItemOutDTO.setPriceQuantity(
                        cartItem.getPrice() + " x " +  cartItem.getQuantity()
                                + " = " + cartItem.getPrice() * cartItem.getQuantity()
                );
                totalAmount += cartItem.getPrice() * cartItem.getQuantity();
                cartItemOutDTOS.add(cartItemOutDTO);
            } catch (FeignException.NotFound e) {
                log.error("Food Not Found : {}", cartItem.getFoodId());
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            } catch (FeignException e) {
                log.error("Restaurant Service Down");
                throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
            }
        }
        CartItemsListOutDTO cartItemsListOutDTO = new CartItemsListOutDTO();
        cartItemsListOutDTO.setCartItemOutDTOList(cartItemOutDTOS);
        cartItemsListOutDTO.setTotalAmount(totalAmount);
        return cartItemsListOutDTO;
    }

    /**
     * Updates the quantity of a cart item based on its ID.
     *
     * @param cartItemId the ID of the cart item to be updated
     * @param index      the number by which to update the quantity (positive for increment, negative for decrement)
     * @return A message indicating the cart was updated successfully
     * @throws ResourceNotFoundException if the cart item is not found
     */
    @Override
    public String updateCartItem(final long cartItemId, final int index) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
            log.error("Cart Item Not Found : {}", cartItemId);
            throw new ResourceNotFoundException(Constants.CART_ITEM_NOT_FOUND);
        }
        cartItem.setQuantity(cartItem.getQuantity() + index);
        cartItemRepository.save(cartItem);
        return Constants.CART_ITEM_UPDATED_SUCCESSFULLY;
    }
}
