package com.food.zotatoFoods.services.impl;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.entites.*;
import com.food.zotatoFoods.entites.enums.Role;
import com.food.zotatoFoods.exceptions.RuntimeConfilictException;
import com.food.zotatoFoods.services.AdminService;
import com.food.zotatoFoods.services.DeliveryPartnerService;
import com.food.zotatoFoods.services.RestaurantPartnerService;
import com.food.zotatoFoods.services.RestaurantService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final DeliveryPartnerService deliveryPartnerService;
    private final RestaurantPartnerService restaurantPartnerService;

    @SuppressWarnings("unlikely-arg-type")
    @Override
    @Transactional
    public RestaurantPartnerDto onBoardNewRestaurantPartner(Long UserId, RestaurantPartnerDto restaurantPartnerDto) {
        // Check User Already Exist with provided userId.
        User user = userService.getUserFromId(UserId);
        // Check User role is RESTRORENT OWNER
        if (user.getRole().equals(Role.RESTAURENT_PARTNER)) {
            throw new RuntimeConfilictException("User is Already a RESTAURENT_PARTNER with userID +" + UserId);
        }
        user.setRole(Set.of(Role.RESTAURENT_PARTNER));
        RestaurantPartner restaurantPartner = modelMapper.map(restaurantPartnerDto, RestaurantPartner.class);
        restaurantPartner.setUser(user);
        RestaurantPartner savedrestaurantPartner = restaurantPartnerService
                .createNewRestaurantPartner(restaurantPartner);
        return modelMapper.map(savedrestaurantPartner, RestaurantPartnerDto.class);
    }

    @Override
    public Page<RestaurantDto> getAllRestaurant(PageRequest pageRequest) {
        return restaurantService.findAllRestaurant(pageRequest)
                .map(Restaurant -> modelMapper.map(Restaurant, RestaurantDto.class));
    }

    @Override
    public Page<DeliveryPartnerDto> getAllDeliveryPartner(PageRequest pageRequest) {
        return deliveryPartnerService.getAllDeliveryPartner(pageRequest)
                .map(DeliveryPartner -> modelMapper.map(DeliveryPartner, DeliveryPartnerDto.class));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    @Transactional
    public DeliveryPartnerDto onBoardDeliveryPartner(Long UserId, DeliveryPartnerDto deliveryPartnerDto) {
        // Check User Already Exist.
        User user = userService.getUserFromId(UserId);
        // Check User role is DELIVERY_PARTNER
        if (user.getRole().equals(Role.DELIVERY_PARTNER)) {
            throw new RuntimeConfilictException("User Role is Already DELIVERY_PARTNER with userID +" + UserId);
        }
        // Set User Role
        user.setRole(Set.of(Role.DELIVERY_PARTNER));
        DeliveryPartner deliveryPartner = modelMapper.map(deliveryPartnerDto, DeliveryPartner.class);
        deliveryPartner.setUser(user);
        return deliveryPartnerService.AddNewDeliveryPartner(deliveryPartner);
    }

    @Override
    public Boolean removeDeliveryPartner(Long UserId) {
        return deliveryPartnerService.removeDeliveryPartner(UserId);
    }

    @Override
    public Boolean removeRestaurant(Long RestaurantId) {
        return restaurantService.removeRestaurant(RestaurantId);
    }

    @Override
    public Boolean varifyRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        // check if already varified.
        if (restaurant.getIsAvailable()) {
            throw new RuntimeConfilictException("Restaurant is Already Varified with Restaurant Id = " + restaurantId);
        }
        restaurant.setIsVarified(true);
        return restaurantService.save(restaurant);

    }

}
