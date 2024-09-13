package com.capstone.users_service.dto;

import lombok.Data;

@Data
public class ContactUsInDTO {
    private String restaurantEmail;
    private String subject;
    private String message;
    private String fromEmail;
}
