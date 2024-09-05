package com.capstone.orders_service.serviceImpl;

import com.capstone.orders_service.converters.CartConverter;
import com.capstone.orders_service.dto.AddToCartInDTO;
import com.capstone.orders_service.entity.CartItem;
import com.capstone.orders_service.exceptions.CartItemDoesNotExistsException;
import com.capstone.orders_service.exceptions.RestaurantConflictException;
import com.capstone.orders_service.feignClient.UsersFeignClient;
import com.capstone.orders_service.repository.CartRepository;
import com.capstone.orders_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UsersFeignClient usersFeignClient;
    @Override
    public String addToCart(AddToCartInDTO addToCartInDTO) {
        CartItem cartItem = CartConverter.addToCartInDTOToCartEntity(addToCartInDTO);
        List<CartItem> cartItemList = cartRepository.findByUserId(addToCartInDTO.getUserId());
        if (!cartItemList.isEmpty()) {
            if (addToCartInDTO.getRestaurantId() != cartItemList.get(0).getRestaurantId()) {
                throw new RestaurantConflictException("You cannot order from 2 restaurants");
            }
            CartItem cartItemItemAlreadyPresent = cartRepository.findByRestaurantIdAndUserIdAndFoodId(addToCartInDTO.getRestaurantId(), addToCartInDTO.getUserId(), addToCartInDTO.getFoodId());
            if (cartItemItemAlreadyPresent != null) {
                cartItemItemAlreadyPresent.setQuantity(cartItemItemAlreadyPresent.getQuantity() + 1);
                cartRepository.save(cartItemItemAlreadyPresent);
                return "Added To Cart";
            }
        }
        cartRepository.save(cartItem);
        return "Added To Cart";
    }

    @Override
    public String deleteCartItem(long cartItemId) {
        CartItem cartItem = cartRepository.findById(cartItemId);
        if (cartItem == null) {
            throw new CartItemDoesNotExistsException("Item Not Found");
        }
        cartRepository.deleteById(cartItemId);
        return "Cart Item Deleted";
    }
}
