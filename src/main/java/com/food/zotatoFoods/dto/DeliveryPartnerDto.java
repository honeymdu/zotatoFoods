package com.food.zotatoFoods.dto;

import org.locationtech.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerDto {

    private String vehicleId;
    private Point currentLocation;

}
