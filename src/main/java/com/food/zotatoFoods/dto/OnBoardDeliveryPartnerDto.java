package com.food.zotatoFoods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardDeliveryPartnerDto {

    private String vehicleId;
    private PointDto currentLocation;

}
