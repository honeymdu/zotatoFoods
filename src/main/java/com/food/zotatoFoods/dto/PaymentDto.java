package com.food.zotatoFoods.dto;

import java.time.LocalDateTime;

import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.entites.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private PaymentMethod paymentMethod;
    private OrderDto order;
    private Double amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentTime;

}
