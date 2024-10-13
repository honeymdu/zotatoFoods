package com.food.zotatoFoods.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private ConsumerDto consumerDto;
    private RestaurantDto restaurantDto;
    private List<CartItemDto> cartItemsDto = new ArrayList<>();
    private Double totalPrice;
    private Double deliveryFee;
    private Double foodAmount;
    private Boolean ValidCart;

}
