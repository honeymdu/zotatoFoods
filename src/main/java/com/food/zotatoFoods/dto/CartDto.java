package com.food.zotatoFoods.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private ConsumerDto consumer;
    private RestaurantDto restaurant;
    private List<CartItemDto> cartItemsDto;
    private Double totalPrice;
    private Boolean ValidCart;

}
