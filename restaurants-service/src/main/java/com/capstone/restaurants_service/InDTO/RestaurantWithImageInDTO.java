package com.capstone.restaurants_service.InDTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Data
public class RestaurantWithImageInDTO {
    @Valid
    private RestaurantInDTO restaurant;

    private MultipartFile image;
}
