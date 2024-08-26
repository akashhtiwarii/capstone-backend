package com.capstone.users_service.utils;

/**
 * Constants for storing constants used throughout.
 */
public class Constants {
    /**
     * Minimum password length.
     */
    public static final int MIN_PASSWORD_LENGTH = 8;
    /**
     * Pincode length.
     */
    public static final long MIN_PINCODE_VALUE = 100000;
    /**
     * Pincode length.
     */
    public static final long MAX_PINCODE_VALUE = 999999;
    /**
     * Phone number length.
     */
    public static final int PHONE_NUMBER_LENGTH = 10;
    /**
     * User Endpoint.
     */
    public static final String USER_ENDPOINT = "/user";
    /**
     * User Registration Endpoint.
     */
    public static final String USER_REGISTER_ENDPOINT = "/register";
    /**
     * User Login Endpoint.
     */
    public static final String USER_LOGIN_ENDPOINT = "/login";
    /**
     * User Addresses Endpoint.
     */
    public static final String USER_ADDRESS_ENDPOINT = "/address";
    /**
     * User Endpoint To Add New Address.
     */
    public static final String USER_ADD_ADDRESS_ENDPOINT = "/address/add";
    /**
     * Initial Wallet Amount.
     */
    public static final Double INITIAL_WALLET_AMOUNT = 1000.0;
    /**
     * Invalid Credentials.
     */
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    /**
     * Owner Signup Message.
     */
    public static final String OWNER_SIGNUP_MESSAGE = "Account added successfully!";
    /**
     * User Signup Message.
     */
    public static final String USER_SIGNUP_MESSAGE = "Account Added Successfully!"
            + " You received 1000 bonus in your wallet.";
    /**
     * Email already in use.
     */
    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    /**
     * Unexpected Error.
     */
    public static final String UNEXPECTED_ERROR = "An unexpected error occured: ";

}
