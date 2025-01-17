package com.food.zotatoFoods.dto;

import java.util.List;

import com.food.zotatoFoods.entites.MenuItem;
import com.food.zotatoFoods.entites.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMenu {

    private String name;
    private List<MenuItem> menuItem;
    private Restaurant restaurant;

}
