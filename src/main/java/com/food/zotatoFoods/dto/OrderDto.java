package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import com.food.zotatoFoods.entites.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  
    private Long id;
    private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private Double totalPrice;
    private Double foodAmount;
    private Double platformFee;
    private PointDto pickupLocation;
    private PointDto dropoffLocation;
    private PaymentDto payment;
    private OrderStatus orderStatus;
    private LocalDateTime OrderCreationTime;
    private RestaurantDto restaurant;
    private DeliveryPartnerDto deliveryPartner;
    private LocalDateTime DeliveryTime;
    private String otp;


}
