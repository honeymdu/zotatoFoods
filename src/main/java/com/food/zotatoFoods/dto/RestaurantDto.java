package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private String name;
    private PointDto restaurantLocation;
    private Double rating = 0.0;
    private String GstNumber;
    private Boolean isAvailable = true;
    private MenuDto menu;
    private RestaurantPartnerDto RestaurantOwner;
    private List<OrderDto> Orders;

}
