package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.FoodCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {

    private String imageUrl;
    private String name;
    private String dishDescription;
    private Double price;
    private FoodCategory foodCategory;
    private List<String> ingredients;
    private Boolean isAvailable;

}
