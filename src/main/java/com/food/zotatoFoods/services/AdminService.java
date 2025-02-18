package com.food.zotatoFoods.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.OnBoardDeliveryPartnerDto;
import com.food.zotatoFoods.dto.OnBoardRestaurantPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;

public interface AdminService {

    RestaurantPartnerDto onBoardNewRestaurantPartner(Long UserId,
            OnBoardRestaurantPartnerDto onBoardRestaurantPartnerDto);

    DeliveryPartnerDto onBoardDeliveryPartner(Long UserId, OnBoardDeliveryPartnerDto onBoardDeliveryPartnerDto);

    Page<RestaurantDto> getAllRestaurant(PageRequest pageRequest);

    Page<DeliveryPartnerDto> getAllDeliveryPartner(PageRequest pageRequest);

    Boolean varifyRestaurant(Long restaurantId);

}
