package com.food.zotatoFoods.dto;

import java.util.ArrayList;
import java.util.List;

import com.food.zotatoFoods.entites.CartItem;
import com.food.zotatoFoods.entites.Consumer;
import com.food.zotatoFoods.entites.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Consumer consumer;
    private Restaurant restaurant;
    private List<CartItem> cartItems = new ArrayList<>();
    private Double totalPrice;
    private Double deliveryFee;
    private Double foodAmount;
    private Boolean ValidCart;

}
