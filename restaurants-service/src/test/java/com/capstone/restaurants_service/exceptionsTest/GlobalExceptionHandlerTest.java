package com.capstone.restaurants_service.exceptionsTest;

import com.capstone.restaurants_service.exceptions.ErrorResponse;
import com.capstone.restaurants_service.exceptions.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // This can be used to set up any common configurations or mocks if needed
    }

    @Test
    void handleInvalidCategoryException() throws Exception {
        String errorMessage = "Invalid category";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/invalidCategory") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleFoodItemNotFoundException() throws Exception {
        String errorMessage = "Food item not found";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/foodItemNotFound") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleUserNotValidException() throws Exception {
        String errorMessage = "User is not valid";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/userNotValid") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), errorMessage))));
    }

    @Test
    void handleUserNotFoundException() throws Exception {
        String errorMessage = "User not found";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/userNotFound") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleFoodAlreadyExistsException() throws Exception {
        String errorMessage = "Food already exists";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/foodAlreadyExists") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage))));
    }

    @Test
    void handleCategoryNotFoundException() throws Exception {
        String errorMessage = "Category not found";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/categoryNotFound") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleCategoryAlreadyExistException() throws Exception {
        String errorMessage = "Category already exists";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/categoryAlreadyExists") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleRestaurantsNotFoundException() throws Exception {
        String errorMessage = "Restaurants not found";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/restaurantsNotFound") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage))));
    }

    @Test
    void handleEmailAlreadyExistsException() throws Exception {
        String errorMessage = "Email already exists";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/emailAlreadyExists") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage))));
    }

    @Test
    void handleRuntimeException() throws Exception {
        String errorMessage = "Runtime error occurred";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/runtimeException") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage))));
    }

    @Test
    void handleGeneralException() throws Exception {
        String errorMessage = "An unexpected error occurred";
        mockMvc.perform(MockMvcRequestBuilders.get("/test/generalException") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage))));
    }

    @Test
    void handleValidationException() throws Exception {
        String fieldName = "fieldName";
        String errorMessage = "Validation error";
        mockMvc.perform(MockMvcRequestBuilders.post("/test/validationException") // replace with a real endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"invalidField\": \"value\" }")) // provide invalid data
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"" + fieldName + "\":\"" + errorMessage + "\"}"));
    }
}
