package com.food.zotatoFoods.dto;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewRestaurantDto {

    @Nonnull
    private String name;
    @Nonnull
    private PointDto restaurantLocation;
    @Nonnull
    private String gstNumber;

}
