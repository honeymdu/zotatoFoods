package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.FoodCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {

    private Long id;
    private String imageUrl;

    @NotBlank(message = "Menu item name is required")
    private String name;

    private String dishDescription;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Food category is required")
    private FoodCategory foodCategory;

    private List<String> ingredients;
    private Boolean isAvailable;

}
