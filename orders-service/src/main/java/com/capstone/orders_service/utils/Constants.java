package com.capstone.orders_service.utils;

/**
 * Utility class for storing constant values used throughout the application.
 * This includes API endpoint paths, validation messages, and general responses.
 * <p>
 * This class is final to prevent inheritance, and the constructor is private
 * to ensure it's not instantiated, as it only contains static members.
 * </p>
 */
public final class Constants {

    /**
     * Private Constructor for utility class.
     */
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Time in seconds available to cancel the order.
     */
    public static final int TIME_TO_CANCEL_ORDER = 30;

    /**
     * Endpoint for retrieving restaurant orders.
     */
    public static final String GET_RESTAURANT_ORDERS = "/myorders";

    /**
     * Endpoint for adding a new order.
     */
    public static final String ADD_ORDER = "/add";

    /**
     * Endpoint for retrieving the user's cart.
     */
    public static final String MY_CART = "/mycart";

    /**
     * Endpoint for adding an item to the cart.
     */
    public static final String ADD_TO_CART = "/cart/add";

    /**
     * Endpoint for updating an order.
     */
    public static final String UPDATE_ORDER = "/update";

    /**
     * Endpoint for retrieving the details of a restaurant order by its ID.
     */
    public static final String GET_RESTAURANT_ORDER_DETAILS = "/restaurantId";

    /**
     * Endpoint for retrieving the orders placed by a user.
     */
    public static final String GET_USER_ORDERS = "/user/orders";

    /**
     * Endpoint for updating an item in the cart.
     */
    public static final String UPDATE_CART = "/cart/update";

    /**
     * Endpoint for deleting an item from the cart.
     */
    public static final String DELETE_CART = "/cart/delete";

    /**
     * Endpoint for canceling an order.
     */
    public static final String CANCEL_ORDER = "/cancel";

    /**
     * Error message for an invalid user ID.
     */
    public static final String USER_ID_NOT_VALID = "User ID not Valid";

    /**
     * Error message for an invalid restaurant ID.
     */
    public static final String RESTAURANT_ID_NOT_VALID = "Restaurant ID not Valid";

    /**
     * Error message for an invalid address ID.
     */
    public static final String ADDRESS_ID_NOT_VALID = "Address ID not Valid";

    /**
     * Error message for an invalid owner ID.
     */
    public static final String OWNER_ID_NOT_VALID = "Owner ID not Valid";

    /**
     * Error message for an invalid order ID.
     */
    public static final String ORDER_ID_NOT_VALID = "Order ID not Valid";

    /**
     * Error message for an invalid cart item ID.
     */
    public static final String CART_ID_NOT_VALID = "Cart Item ID not Valid";

    /**
     * Error message for an invalid food ID.
     */
    public static final String FOOD_ID_NOT_VALID = "Food ID not Valid";

    /**
     * Validation message for requiring a valid quantity.
     */
    public static final String VALID_QUANTITY_REQUIRED = "Valid Quantity Required";

    /**
     * Error message when a user is not found.
     */
    public static final String USER_NOT_FOUND = "User Not Found";

    /**
     * Error message when a food item is not found.
     */
    public static final String FOOD_ITEM_NOT_FOUND = "Food Item Not Found";

    /**
     * Error message when a cart item is not found.
     */
    public static final String CART_ITEM_NOT_FOUND = "Cart Item Not Found";

    /**
     * Success message for when a cart item is added successfully.
     */
    public static final String CART_ITEM_ADDED_SUCCESSFULLY = "Cart Item Added Successfully";

    /**
     * Success message for when a cart item is updated successfully.
     */
    public static final String CART_ITEM_UPDATED_SUCCESSFULLY = "Cart Item Update Successfully";

    /**
     * Success message for when a cart item is deleted successfully.
     */
    public static final String CART_ITEM_DELETED_SUCCESSFULLY = "Cart Item Deleted Successfully";

    /**
     * Error message when attempting to order from multiple restaurants.
     */
    public static final String RESTAURANT_CONFLICT = "You cannot order from 2 restaurants";

    /**
     * Error message for an invalid user.
     */
    public static final String USER_NOT_VALID = "User Not Valid";

    /**
     * Error message when an order is not found.
     */
    public static final String ORDER_NOT_FOUND = "Order Not Found";

    /**
     * Error message when data is not found.
     */
    public static final String DATA_NOT_FOUND = "Data Not Found";

    /**
     * Error message when an address is not found.
     */
    public static final String ADDRESS_NOT_FOUND = "Address Not Found";

    /**
     * Success message for when an order is added successfully.
     */
    public static final String ORDER_ADDED_SUCCESSFULLY = "Order Added Successfully";

    /**
     * Success message for when an order is updated successfully.
     */
    public static final String ORDER_UPDATED_SUCCESSFULLY = "Order Updated Successfully";

    /**
     * Success message for when an order is deleted successfully.
     */
    public static final String ORDER_DELETED_SUCCESSFULLY = "Order Deleted Successfully";

    /**
     * Success message for when an order is canceled successfully.
     */
    public static final String ORDER_CANCELLED_SUCCESSFULLY = "Order Cancelled Successfully";

    /**
     * Error message when the user does not have sufficient funds in their wallet.
     */
    public static final String INSUFFICIENT_WALLET_AMOUNT = "You do not have enough money in your wallet";

    /**
     * Error message when a restaurant is not found.
     */
    public static final String RESTAURANT_NOT_FOUND = "Restaurant Not Found";

    /**
     * Generic error message for unexpected errors.
     */
    public static final String UNEXPECTED_ERROR = "An Unexpected Error Occurred. Try Again!";

    /**
     * Error message when an order cannot be canceled.
     */
    public static final String CANNOT_CANCEL_ORDER = "Order Cannot Be Cancelled Now";

    /**
     * Error message when user service is down.
     */
    public static final String USER_SERVICE_DOWN = "Failed to communicate with User service";

    /**
     * Error message when Restaurant service is down.
     */
    public static final String RESTAURANT_SERVICE_DOWN = "Failed to communicate with Restaurant service";

    /**
     * Error message when service is down.
     */
    public static final String SERVICE_DOWN = "Failed to communicate service";

    /**
     * Order Endpoint.
     */
    public static final String ORDER_ENDPOINT = "/order";

    /**
     * Restaurant ID.
     */
    public static final String RESTAURANT_ID = "/{restaurantId}";

    /**
     * Owner Endpoint.
     */
    public static final String GET_RESTAURANT_BY_OWNER_ID = "/owner";

    /**
     * Get Food Item By Restaurant Endpoint.
     */
    public static final String GET_FOOD_BY_RESTAURANT = "/restaurantfood/{restaurantId}";

    /**
     * Get Food Item By ID Endpoint.
     */
    public static final String GET_FOOD_BY_ID = "/food/id";

    /**
     * Wallet Not Found Message.
     */
    public static final String WALLET_NOT_FOUND = "Wallet Not Found";

    /**
     * Get User By ID.
     */
    public static final String GET_USER_BY_ID_ENDPOINT = "/{id}";

    /**
     * Get Address By ID.
     */
    public static final String GET_ADDRESS_BY_ID = "/address/id";

    /**
     * Address Endpoint.
     */
    public static final String ADDRESS_ENDPOINT = "/address";

    /**
     * Wallet Endpoint.
     */
    public static final String WALLET_ENDPOINT = "/wallet";

    /**
     * Update Wallet Endpoint.
     */
    public static final String WALLET_UPDATE = "/wallet/update";
}
