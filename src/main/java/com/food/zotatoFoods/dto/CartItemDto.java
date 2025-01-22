package com.food.zotatoFoods.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private Integer quantity;
    private MenuItemDto menuItem;
    private Double totalPrice;

}
