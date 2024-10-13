package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantPartnerDto {

    private Long id;
    private Long aadharNo;
    private UserDto user;
    private RestaurantDto restaurant;
   
}