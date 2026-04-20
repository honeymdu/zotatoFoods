package com.food.zotatoFoods.dto;

import com.food.zotatoFoods.entites.enums.PaymentMethod;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {

    @NotNull(message = "User location is required")
    @Valid
    private PointDto userLocation;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}
