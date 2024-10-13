package com.food.zotatoFoods.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;

public interface AdminService {

    public RestaurantPartnerDto onBoardNewRestaurantPartner(Long UserId , RestaurantPartnerDto RestaurantPartnerDto);

    public DeliveryPartnerDto onBoardDeliveryPartner(Long UserId, DeliveryPartnerDto deliveryPartnerDto);

    Page<RestaurantDto> getAllRestaurant(PageRequest pageRequest);

    Page<DeliveryPartnerDto> getAllDeliveryPartner(PageRequest pageRequest);

    public Boolean removeDeliveryPartner(Long UserId);

    public Boolean removeRestaurant(Long RestaurantId);

}
