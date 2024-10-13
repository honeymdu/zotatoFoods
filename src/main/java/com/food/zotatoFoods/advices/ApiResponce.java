package com.food.zotatoFoods.advices;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiResponce<T> {

    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    // Constructor for data response
    public ApiResponce(T data) {
        this();
        this.data = data;
    }

    // Constructor for error response
    public ApiResponce(ApiError error) {
        this(); 
        this.error = error;
    }

    // No-arg constructor to initialize timestamp
    public ApiResponce() {
        this.timestamp = LocalDateTime.now();
    }

    
}
