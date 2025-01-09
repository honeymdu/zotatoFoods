package com.food.zotatoFoods.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.RestaurantPartnerRepository;
import com.food.zotatoFoods.services.RestaurantPartnerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    public final RestaurantPartnerRepository restaurantPartnerRepository;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRestaurant'");
    }

    @Override
    public RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRestaurantDetails'");
    }

    @Override
    public OrderDto acceptOrderRequest(Long orderRequestId, Integer estimatedPreparationTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptOrderRequest'");
    }

    @Override
    public OrderRequestsDto cancelOrderRequest(Long orderRequestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrderRequest'");
    }

    @Override
    public MenuItemDto updateMenuItemOfMenu(MenuItemDto menuItemDto, Long menuItemId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMenuItemOfMenu'");
    }

    @Override
    public RestaurantPartner getCurrentRestaurantPartner() {
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return restaurantPartnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver Not Found assosiated with user with userId =" + user.getId()));
    }

    @Override
    public MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMenuItemForMenu'");
    }

    @Override
    public RestaurantPartner createNewRestaurantPartner(RestaurantPartner restaurantPartner) {
        return restaurantPartnerRepository.save(restaurantPartner);
    }

}
