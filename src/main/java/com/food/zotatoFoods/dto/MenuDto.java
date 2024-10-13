package com.food.zotatoFoods.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    private String menuName;
    private List<MenuItemDto> menuItems;
    private Boolean isActive;
    private RestaurantDto restaurant;

}
