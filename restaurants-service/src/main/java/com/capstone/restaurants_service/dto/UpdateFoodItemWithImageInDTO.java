package com.capstone.restaurants_service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Data
public class UpdateFoodItemWithImageInDTO {

    @Valid
    private UpdateFoodItemInDTO updateFoodItemInDTO;

    MultipartFile image;
}
