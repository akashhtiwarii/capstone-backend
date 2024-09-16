package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.dto.CartItemOutDTO;
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
    public String addToCart(AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);
        try {
            UserOutDTO user = usersFeignClient.getUserById(addToCartInDTO.getUserId()).getBody();
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        } catch (FeignException e) {
            throw new RuntimeException(Constants.USER_SERVICE_DOWN);
        }
        try {
            FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
            cartItem.setPrice(cartItem.getQuantity() * foodItemOutDTO.getPrice());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
        } catch (FeignException e) {
            throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
        }
        try {
            List<FoodItemOutDTO> foodItemOutDTOS = restaurantFeignClient.getFoodItemsByRestaurant(
                    addToCartInDTO.getRestaurantId()
            ).getBody();
            if (foodItemOutDTOS.isEmpty()) {
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
            List<Long> foodIds = foodItemOutDTOS.stream().map(FoodItemOutDTO::getFoodId).collect(Collectors.toList());
            if (!foodIds.contains(addToCartInDTO.getFoodId())) {
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            }
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
        } catch (FeignException e) {
            throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
        }
        List<CartItem> cartItemList = cartItemRepository.findByUserId(addToCartInDTO.getUserId());
        if (!cartItemList.isEmpty()) {
            if (addToCartInDTO.getRestaurantId() != cartItemList.get(0).getRestaurantId()) {
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
    public String deleteCartItem(long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
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
    public List<CartItemOutDTO> getCartItems(long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException(Constants.CART_ITEM_NOT_FOUND);
        }
        List<CartItemOutDTO> cartItemOutDTOS = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            try {
                FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
                RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(
                        cartItem.getRestaurantId()
                ).getBody();
                CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();
                cartItemOutDTO.setCartItemId(cartItem.getCartItemId());
                cartItemOutDTO.setUserId(cartItem.getUserId());
                cartItemOutDTO.setFoodName(foodItemOutDTO.getName());
                cartItemOutDTO.setRestaurantName(restaurant.getName());
                cartItemOutDTO.setQuantity(cartItem.getQuantity());
                cartItemOutDTO.setPrice(cartItem.getPrice());
                cartItemOutDTOS.add(cartItemOutDTO);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
            } catch (FeignException e) {
                throw new RuntimeException(Constants.RESTAURANT_SERVICE_DOWN);
            }
        }
        return cartItemOutDTOS;
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
    public String updateCartItem(long cartItemId, int index) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
            throw new ResourceNotFoundException(Constants.FOOD_ITEM_NOT_FOUND);
        }
        cartItem.setQuantity(cartItem.getQuantity() + index);
        cartItemRepository.save(cartItem);
        return Constants.CART_ITEM_UPDATED_SUCCESSFULLY;
    }
}
