package com.food.zotatoFoods.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerDto {

    private Long id;
    private UserDto user;
    private Double rating;

}