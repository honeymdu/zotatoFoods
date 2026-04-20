package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetOrderStatusDto {

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;

}
