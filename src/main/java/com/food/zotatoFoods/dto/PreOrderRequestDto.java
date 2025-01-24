package com.food.zotatoFoods.dto;

import java.math.BigDecimal;

import org.locationtech.jts.geom.Point;

import com.food.zotatoFoods.entites.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PreOrderRequestDto {

    private Long consumerId;
    private Long restaurantId;
    private Cart cart;
    private BigDecimal foodAmount;
    private BigDecimal platformFee;
    private BigDecimal totalPrice;
    private BigDecimal deliveryFee;
    private Point currentLocation;

}
