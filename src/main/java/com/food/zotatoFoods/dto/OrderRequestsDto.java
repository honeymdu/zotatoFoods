package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestsDto {

    private CartDto cart;
    private Double foodAmount;
    private Double platformFee;
    private Double totalPrice;
    private Double deliveryFee;
    private OrderRequestStatus orderRequestStatus;
    private RestaurantDto restaurant;
    private ConsumerDto consumer;
    private PaymentMethod paymentMethod;

}
