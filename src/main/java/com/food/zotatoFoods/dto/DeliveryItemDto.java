package com.food.zotatoFoods.dto;

import java.time.LocalDateTime;
import com.food.zotatoFoods.entites.enums.OrderStatus;

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
public class DeliveryItemDto {

    private Long id;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private OrderDto order;
    private OrderItemDto orderItem;
    private DeliveryPartnerDto deliveryPartner; 

}

