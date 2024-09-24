package com.capstone.orders_service.Enum;

/**
 * Enumeration representing the possible statuses of an order.
 * <p>
 * This enum defines the different stages an order can be in,
 * from its initial placement to its final completion or cancellation.
 * </p>
 */
public enum Status {

    /**
     * Indicates that the order has been placed but is not yet processed.
     * <p>
     * An order in this state is awaiting further action and has not yet moved to the next stage.
     * </p>
     */
    PENDING,

    /**
     * Indicates that the order is currently being processed.
     * <p>
     * The order is actively being prepared or handled, but is not yet complete.
     * </p>
     */
    ONGOING,

    /**
     * Indicates that the order has been successfully completed.
     * <p>
     * The order has been fully processed and is considered finished.
     * </p>
     */
    COMPLETED,

    /**
     * Indicates that the order has been cancelled.
     * <p>
     * The order was terminated before completion and is no longer active.
     * </p>
     */
    CANCELLED
}
