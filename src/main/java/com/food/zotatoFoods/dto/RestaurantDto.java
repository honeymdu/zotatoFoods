package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.MenuItem;
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
    private List<MenuItem> menuItems;
}
