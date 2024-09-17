package com.capstone.users_service.dtoTest;

import com.capstone.users_service.dto.ContactUsInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactUsInDTOTest {

    @Test
    public void testGetterAndSetter() {
        ContactUsInDTO contactUsInDTO = new ContactUsInDTO();

        assertNull(contactUsInDTO.getRestaurantEmail());
        String restaurantEmail = "email@gmail.com";
        contactUsInDTO.setRestaurantEmail(restaurantEmail);
        assertEquals(restaurantEmail, contactUsInDTO.getRestaurantEmail());

        assertNull(contactUsInDTO.getSubject());
        String subject = "Order Issue";
        contactUsInDTO.setSubject(subject);
        assertEquals(subject, contactUsInDTO.getSubject());

        assertNull(contactUsInDTO.getMessage());
        String message = "message";
        contactUsInDTO.setMessage(message);
        assertEquals(message, contactUsInDTO.getMessage());

        assertNull(contactUsInDTO.getFromEmail());
        String fromEmail = "user@example.com";
        contactUsInDTO.setFromEmail(fromEmail);
        assertEquals(fromEmail, contactUsInDTO.getFromEmail());
    }

    @Test
    public void testEqualsAndHashcode() {
        String restaurantEmail = "email@gmail.com";
        String subject = "Order Issue";
        String message = "message";
        String fromEmail = "user@example.com";

        ContactUsInDTO contactUsInDTO1 = buildContactUsInDTO(restaurantEmail, subject, message, fromEmail);

        assertEquals(contactUsInDTO1, contactUsInDTO1);
        assertEquals(contactUsInDTO1.hashCode(), contactUsInDTO1.hashCode());

        assertNotEquals(contactUsInDTO1, new Object());

        ContactUsInDTO contactUsInDTO2 = buildContactUsInDTO(restaurantEmail, subject, message, fromEmail);
        assertEquals(contactUsInDTO1, contactUsInDTO2);
        assertEquals(contactUsInDTO1.hashCode(), contactUsInDTO2.hashCode());

        contactUsInDTO2 = buildContactUsInDTO(restaurantEmail + ".com", subject, message, fromEmail);
        assertNotEquals(contactUsInDTO1, contactUsInDTO2);
        assertNotEquals(contactUsInDTO1.hashCode(), contactUsInDTO2.hashCode());

        contactUsInDTO2 = buildContactUsInDTO(restaurantEmail, subject + " Urgent", message, fromEmail);
        assertNotEquals(contactUsInDTO1, contactUsInDTO2);
        assertNotEquals(contactUsInDTO1.hashCode(), contactUsInDTO2.hashCode());

        contactUsInDTO2 = buildContactUsInDTO(restaurantEmail, subject, message + " Please help.", fromEmail);
        assertNotEquals(contactUsInDTO1, contactUsInDTO2);
        assertNotEquals(contactUsInDTO1.hashCode(), contactUsInDTO2.hashCode());

        contactUsInDTO2 = buildContactUsInDTO(restaurantEmail, subject, message, fromEmail + ".com");
        assertNotEquals(contactUsInDTO1, contactUsInDTO2);
        assertNotEquals(contactUsInDTO1.hashCode(), contactUsInDTO2.hashCode());
    }

    @Test
    public void testToString() {
        ContactUsInDTO contactUsInDTO = new ContactUsInDTO();

        String restaurantEmail = "email@gmail.com";
        String subject = "Order Issue";
        String message = "message";
        String fromEmail = "user@example.com";

        contactUsInDTO.setRestaurantEmail(restaurantEmail);
        contactUsInDTO.setSubject(subject);
        contactUsInDTO.setMessage(message);
        contactUsInDTO.setFromEmail(fromEmail);

        assertEquals("ContactUsInDTO(restaurantEmail=email@gmail.com, subject=Order Issue, message=message, fromEmail=user@example.com)",
                contactUsInDTO.toString());
    }

    private ContactUsInDTO buildContactUsInDTO(String restaurantEmail, String subject, String message, String fromEmail) {
        ContactUsInDTO contactUsInDTO = new ContactUsInDTO();
        contactUsInDTO.setRestaurantEmail(restaurantEmail);
        contactUsInDTO.setSubject(subject);
        contactUsInDTO.setMessage(message);
        contactUsInDTO.setFromEmail(fromEmail);
        return contactUsInDTO;
    }
}