package com.food.zotatoFoods.dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerDto {

    private Long id;
    private Double rating;
    private UserDto user;
    private Boolean available;
    private String vehicleId;
    private PointDto currentLocation;
    private List<DeliveryItemDto> deliveryItems;

}
