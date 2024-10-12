package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  
    private Long id;
    private UserDto userDto;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private Double totalPrice;
    private PointDto pickupLocation;
    private PointDto dropoffLocation;
    private LocalDateTime OrderCreationTime;
    private DeliveryPartnerDto deliveryPartnerDto;
    private LocalDateTime DeliveryTime;

}
