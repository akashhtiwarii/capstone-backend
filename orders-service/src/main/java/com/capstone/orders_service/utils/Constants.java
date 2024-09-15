package com.capstone.orders_service.utils;

/**
 * Constants for storing constants used throughout.
 */
public class Constants {
    /**
     * Time in seconds available to cancel the order.
     */
    public static final int TIME_TO_CANCEL_ORDER = 30;

    public static final String GET_RESTAURANT_ORDERS = "/myorders";
    public static final String ADD_ORDER = "/add";
    public static final String MY_CART = "/mycart";
    public static final String ADD_TO_CART = "/cart/add";
    public static final String UPDATE_ORDER = "/update";
    public static final String GET_RESTAURANT_ORDER_DETAILS = "/restaurantId";
    public static final String GET_USER_ORDERS = "/user/orders";
    public static final String UPDATE_CART = "/cart/update";
    public static final String DELETE_CART = "/cart/delete";
    public static final String CANCEL_ORDER = "/cancel";
    public static final String USER_ID_NOT_VALID = "User ID not Valid";
    public static final String RESTAURANT_ID_NOT_VALID = "Restaurant ID not Valid";
    public static final String ADDRESS_ID_NOT_VALID = "Address ID not Valid";
    public static final String OWNER_ID_NOT_VALID = "Owner ID not Valid";
    public static final String ORDER_ID_NOT_VALID = "Order ID not Valid";
    public static final String CART_ID_NOT_VALID = "Cart Item ID not Valid";
    public static final String FOOD_ID_NOT_VALID = "Food ID not Valid";
    public static final String VALID_QUANTITY_REQUIRED = "Valid Quantity Required";
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String FOOD_ITEM_NOT_FOUND = "Food Item Not Found";
    public static final String CART_ITEM_NOT_FOUND = "Cart Item Not Found";
    public static final String CART_ITEM_ADDED_SUCCESSFULLY = "Cart Item Added Successfully";
    public static final String CART_ITEM_UPDATED_SUCCESSFULLY = "Cart Item Update Successfully";
    public static final String CART_ITEM_DELETED_SUCCESSFULLY = "Cart Item Deleted Successfully";
    public static final String RESTAURANT_CONFLICT = "You cannot order from 2 restaurants";
    public static final String USER_NOT_VALID = "User Not Valid";
    public static final String ORDER_NOT_FOUND = "Order Not Found";
    public static final String DATA_NOT_FOUND = "Data Not Found";
    public static final String ADDRESS_NOT_FOUND = "Address Not Found";
    public static final String ORDER_ADDED_SUCCESSFULLY = "Order Added Successfully";
    public static final String ORDER_UPDATED_SUCCESSFULLY = "Order Updated Successfully";
    public static final String ORDER_DELETED_SUCCESSFULLY = "Order Deleted Successfully";
    public static final String ORDER_CANCELLED_SUCCESSFULLY = "Order Cancelled Successfully";
    public static final String INSUFFICIENT_WALLET_AMOUNT = "You do not have enough money in your wallet";
    public static final String RESTAURANT_NOT_FOUND = "Restaurant Not Found";
    public static final String UNEXPECTED_ERROR = "An Unexpected Error Occurred. Try Again!";
    public static final String CANNOT_CANCEL_ORDER = "Order Cannot Be Cancelled Now";
}
