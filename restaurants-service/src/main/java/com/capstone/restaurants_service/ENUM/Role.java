package com.capstone.restaurants_service.ENUM;

/**
 * Enum representing the different roles a user can have within the system.

 */
public enum Role {

    /**
     * Represents a standard user role.
     * <p>
     * Users with this role typically have basic access rights and functionalities.
     * </p>
     */
    USER,

    /**
     * Represents an owner role.
     * <p>
     * Users with this role typically have elevated access rights, including the ability to manage resources,
     * such as restaurants and food items.
     * </p>
     */
    OWNER
}
