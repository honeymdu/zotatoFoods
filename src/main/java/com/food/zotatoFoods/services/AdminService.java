package com.food.zotatoFoods.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.Restaurant;

public interface AdminService {

    public Restaurant onBoardNewRestaurant(Long UserId, RestaurantDto restaurantDto);

    public DeliveryPartner onBoardDeliveryPartner(Long UserId, DeliveryPartnerDto deliveryPartnerDto);

    Page<Restaurant> getAllRestaurant(PageRequest pageRequest);

    Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest);

    public Boolean removeDeliveryPartner(Long UserId);

    public Boolean removeRestaurant(Restaurant restaurant);

}
