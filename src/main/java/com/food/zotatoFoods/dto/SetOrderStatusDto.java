package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.OrderStatus;

import lombok.Data;

@Data
public class SetOrderStatusDto {

    private OrderStatus orderStatus;

}
