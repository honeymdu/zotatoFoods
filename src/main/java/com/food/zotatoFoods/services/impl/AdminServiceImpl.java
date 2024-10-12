package com.food.zotatoFoods.services.impl;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.DeliveryPartner;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.enums.Role;
import com.food.zotatoFoods.exceptions.RuntimeConfilictException;
import com.food.zotatoFoods.services.AdminService;
import com.food.zotatoFoods.services.DeliveryPartnerService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.services.WalletService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final WalletService walletService;

    @SuppressWarnings("unlikely-arg-type")
    @Override
    @Transactional
    public Restaurant onBoardNewRestaurant(Long UserId, RestaurantDto restaurantDto) {
        // Check User Already Exist.
        User user = userService.getUserFromId(UserId);
        // Check User role is RESTRORENT OWNER
        if (!user.getRole().equals(Role.RESTAURENT_OWNER)) {
            throw new RuntimeException("User Role is Not RESTAURENT_OWNER with userID +" + UserId);
        }
        // Check Restaurant already Exists with same name Corresponding to User
        Restaurant IsrestaurantExist = restaurantService.getRestaurantByUser(user);
        if (IsrestaurantExist.getName().equals(restaurantDto.getName())) {
            throw new RuntimeConfilictException("Restaurant Already Exist with User id" + UserId
                    + " and with Restaurant Name " + restaurantDto.getName());
        }
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        restaurant.setRestaurantOwner(user);
        // Add New Restaurant
        return restaurantService.save(restaurant);
    }

    @Override
    public Page<Restaurant> getAllRestaurant(PageRequest pageRequest) {
        return restaurantService.findAllRestaurant(pageRequest);
    }

    @Override
    public Page<DeliveryPartner> getAllDeliveryPartner(PageRequest pageRequest) {
        return deliveryPartnerService.getAllDeliveryPartner(pageRequest);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    @Transactional
    public DeliveryPartner onBoardDeliveryPartner(Long UserId, DeliveryPartnerDto deliveryPartnerDto) {
        // Check User Already Exist.
        User user = userService.getUserFromId(UserId);
        // Check User role is RESTRORENT OWNER
        if (!user.getRole().equals(Role.DELIVERY_PARTNER)) {
            throw new RuntimeConfilictException("User Role is Already DELIVERY_PARTNER with userID +" + UserId);
        }
        // Set User Role
        user.setRole(Role.DELIVERY_PARTNER);
        User savedUser = userService.save(user);
        DeliveryPartner deliveryPartner = modelMapper.map(deliveryPartnerDto, DeliveryPartner.class);
        deliveryPartner.setUser(savedUser);
        walletService.createNewWallet(savedUser);
        return deliveryPartnerService.AddNewDeliveryPartner(deliveryPartner);
    }

    @Override
    public Boolean removeDeliveryPartner(Long UserId) {
        return deliveryPartnerService.removeDeliveryPartner(UserId);
    }

    @Override
    public Boolean removeRestaurant(Restaurant restaurant) {
        return restaurantService.removeRestaurant(restaurant);
    }
}
