package com.capstone.orders_service.dtoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.capstone.orders_service.dto.CartItemOutDTO;
import com.capstone.orders_service.dto.CartItemsListOutDTO;
import org.junit.jupiter.api.Test;

public class CartItemsListOutDTOTest {

    @Test
    public void testGetterAndSetter() {
        CartItemsListOutDTO cartItemsListOutDTO = new CartItemsListOutDTO();

        assertEquals(new ArrayList<>(), cartItemsListOutDTO.getCartItemOutDTOList());
        List<CartItemOutDTO> cartItemList = new ArrayList<>();
        cartItemsListOutDTO.setCartItemOutDTOList(cartItemList);
        assertEquals(cartItemList, cartItemsListOutDTO.getCartItemOutDTOList());

        assertEquals(0.0, cartItemsListOutDTO.getTotalAmount());
        double totalAmount = 100.50;
        cartItemsListOutDTO.setTotalAmount(totalAmount);
        assertEquals(totalAmount, cartItemsListOutDTO.getTotalAmount());
    }

    @Test
    public void testToString() {
        CartItemsListOutDTO cartItemsListOutDTO = new CartItemsListOutDTO();

        List<CartItemOutDTO> cartItemList = new ArrayList<>();
        cartItemsListOutDTO.setCartItemOutDTOList(cartItemList);

        double totalAmount = 100.50;
        cartItemsListOutDTO.setTotalAmount(totalAmount);

        String expectedString = "CartItemsListOutDTO(cartItemOutDTOList=" + cartItemList + ", totalAmount=" + totalAmount + ")";
        assertEquals(expectedString, cartItemsListOutDTO.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        List<CartItemOutDTO> cartItemList = new ArrayList<>();
        double totalAmount1 = 100.50;
        double totalAmount2 = 200.50;

        CartItemsListOutDTO cartItemsListOutDTO1 = buildCartItemsListOutDTO(cartItemList, totalAmount1);

        assertEquals(cartItemsListOutDTO1, cartItemsListOutDTO1);
        assertEquals(cartItemsListOutDTO1.hashCode(), cartItemsListOutDTO1.hashCode());

        assertNotEquals(cartItemsListOutDTO1, new Object());

        CartItemsListOutDTO cartItemsListOutDTO2 = buildCartItemsListOutDTO(cartItemList, totalAmount1);
        assertEquals(cartItemsListOutDTO1, cartItemsListOutDTO2);
        assertEquals(cartItemsListOutDTO1.hashCode(), cartItemsListOutDTO2.hashCode());

        cartItemsListOutDTO2 = buildCartItemsListOutDTO(new ArrayList<>(), totalAmount2);
        assertNotEquals(cartItemsListOutDTO1, cartItemsListOutDTO2);
        assertNotEquals(cartItemsListOutDTO1.hashCode(), cartItemsListOutDTO2.hashCode());

        cartItemsListOutDTO2 = buildCartItemsListOutDTO(cartItemList, totalAmount2);
        assertNotEquals(cartItemsListOutDTO1, cartItemsListOutDTO2);
        assertNotEquals(cartItemsListOutDTO1.hashCode(), cartItemsListOutDTO2.hashCode());

        cartItemsListOutDTO1 = new CartItemsListOutDTO();
        cartItemsListOutDTO2 = new CartItemsListOutDTO();
        assertEquals(cartItemsListOutDTO1, cartItemsListOutDTO2);
        assertEquals(cartItemsListOutDTO1.hashCode(), cartItemsListOutDTO2.hashCode());
    }

    private CartItemsListOutDTO buildCartItemsListOutDTO(List<CartItemOutDTO> cartItemList, double totalAmount) {
        CartItemsListOutDTO cartItemsListOutDTO = new CartItemsListOutDTO();
        cartItemsListOutDTO.setCartItemOutDTOList(cartItemList);
        cartItemsListOutDTO.setTotalAmount(totalAmount);
        return cartItemsListOutDTO;
    }
}

