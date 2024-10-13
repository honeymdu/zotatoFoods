package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private MenuItemDto menuItem;
    private Integer quantity;
    private Double totalPrice;
    private CartDto cart;

}
