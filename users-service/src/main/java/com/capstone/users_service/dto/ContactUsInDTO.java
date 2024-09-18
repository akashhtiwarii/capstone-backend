package com.capstone.users_service.dto;

import lombok.Data;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) for sending contact messages.
 * This DTO encapsulates the details of a message that a user wants to send to the restaurant's support team.
 */
@Data
public class ContactUsInDTO {
    /**
     * Email address of the sender.
     * This field specifies the email address from which the contact message is being sent.
     */
    private String fromEmail;

    /**
     * Subject of the contact message.
     * This field provides a brief summary or topic of the message being sent.
     */
    private String subject;

    /**
     * Content of the contact message.
     * This field contains the main body of the message that the user wants to send to the restaurant.
     */
    private String message;

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two {@code ContactUsInDTO} objects are considered equal if all their fields are equal.
     *
     * @param o the object to compare this {@code ContactUsInDTO} against
     * @return {@code true} if the given object is equal to this {@code ContactUsInDTO}; {@code false} otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactUsInDTO that = (ContactUsInDTO) o;
        return Objects.equals(subject, that.subject)
                && Objects.equals(message, that.message)
                && Objects.equals(fromEmail, that.fromEmail);
    }

    /**
     * Returns a hash code value for the object.
     * The hash code is computed based on all fields of the {@code ContactUsInDTO} object.
     *
     * @return a hash code value for this {@code ContactUsInDTO}
     */
    @Override
    public int hashCode() {
        return Objects.hash(subject, message, fromEmail);
    }
}
