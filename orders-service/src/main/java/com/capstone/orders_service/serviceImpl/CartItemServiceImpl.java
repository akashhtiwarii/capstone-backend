package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.*;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.*;
import com.capstone.orders_service.feignClient.RestaurantFeignClient;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartItemRepository;
import com.capstone.orders_service.service.CartItemService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UsersFeignClient usersFeignClient;

    @Autowired
    private RestaurantFeignClient restaurantFeignClient;

    @Override
    public String addToCart(AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);
        try {
            UserOutDTO user = usersFeignClient.getUserById(addToCartInDTO.getUserId()).getBody();
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("User Not Found");
        }
        try {
            FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
            cartItem.setPrice(cartItem.getQuantity() * foodItemOutDTO.getPrice());
        } catch (FeignException.NotFound e) {
            throw new FoodItemNotFoundException("Food Item Not Found");
        }
        try {
            List<FoodItemOutDTO> foodItemOutDTOS = restaurantFeignClient.getFoodItemsByRestaurant(addToCartInDTO.getRestaurantId()).getBody();
            if (foodItemOutDTOS.isEmpty()) {
                throw new FoodItemNotFoundException("Food Item Not Present");
            }
            List<Long> foodIds = foodItemOutDTOS.stream().map(FoodItemOutDTO::getFoodId).collect(Collectors.toList());
            if (!foodIds.contains(addToCartInDTO.getFoodId())) {
                throw new FoodItemNotFoundException("Food Item Not Present");
            }
        } catch (FeignException.NotFound e) {
            throw new FoodItemNotFoundException("Food Item Not Present");
        }
        List<CartItem> cartItemList = cartItemRepository.findByUserId(addToCartInDTO.getUserId());
        if (!cartItemList.isEmpty()) {
            if (addToCartInDTO.getRestaurantId() != cartItemList.get(0).getRestaurantId()) {
                throw new RestaurantConflictException("You cannot order from 2 restaurants");
            }
            CartItem cartItemItemAlreadyPresent = cartItemRepository.findByRestaurantIdAndUserIdAndFoodId(addToCartInDTO.getRestaurantId(), addToCartInDTO.getUserId(), addToCartInDTO.getFoodId());
            if (cartItemItemAlreadyPresent != null) {
                cartItemItemAlreadyPresent.setQuantity(cartItemItemAlreadyPresent.getQuantity() + 1);
                cartItemRepository.save(cartItemItemAlreadyPresent);
                return "Added To Cart";
            }
        }
        cartItemRepository.save(cartItem);
        return "Added To Cart";
    }

    @Override
    public String deleteCartItem(long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
            throw new CartItemDoesNotExistsException("Item Not Found");
        }
        cartItemRepository.deleteById(cartItemId);
        return "Cart Item Deleted";
    }

    @Override
    public List<CartItemOutDTO> getCartItems(long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new CartItemDoesNotExistsException("No Items in Cart");
        }
        List<CartItemOutDTO> cartItemOutDTOS = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            try {
                FoodItemOutDTO foodItemOutDTO = restaurantFeignClient.getFoodItemById(cartItem.getFoodId()).getBody();
                RestaurantOutDTO restaurant = restaurantFeignClient.getRestaurantById(cartItem.getRestaurantId()).getBody();
                CartItemOutDTO cartItemOutDTO = new CartItemOutDTO();
                cartItemOutDTO.setCartItemId(cartItem.getCartItemId());
                cartItemOutDTO.setUserId(cartItem.getUserId());
                cartItemOutDTO.setFoodName(foodItemOutDTO.getName());
                cartItemOutDTO.setRestaurantName(restaurant.getName());
                cartItemOutDTO.setQuantity(cartItem.getQuantity());
                cartItemOutDTO.setPrice(cartItem.getPrice());
                cartItemOutDTOS.add(cartItemOutDTO);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Food Item Not Found");
            }
        }
        return cartItemOutDTOS;
    }

    /**
     * @param cartItemId
     * @return
     */
    @Override
    public String updateCartItem(long cartItemId, int index) {
       CartItem cartItem = cartItemRepository.findById(cartItemId);
       if (cartItem == null) {
           throw new CartItemDoesNotExistsException("Cart Item is not present");
       }
       cartItem.setQuantity(cartItem.getQuantity() + index);
       cartItemRepository.save(cartItem);
       return "Cart Updated Successfully";
    }
}
