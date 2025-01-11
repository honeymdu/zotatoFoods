package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.RestaurantPartnerRepository;
import com.food.zotatoFoods.services.RestaurantPartnerService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Override
    public RestaurantDto createRestaurant(@Validated AddNewRestaurantDto addNewRestaurantDto) {
        RestaurantDto restaurant = modelMapper.map(addNewRestaurantDto, RestaurantDto.class);
        Restaurant savedRestaurant = restaurantService.AddNewRestaurant(getCurrentRestaurantPartner(), restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
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

    @Override
    public Restaurant ViewMyRestaurantProfile(Long RestaurantId) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.viewProfile(RestaurantId);
        if (!restaurant.getRestaurantPartner().equals(restaurantPartner)) {
            throw new RuntimeException("Restaurant is not assositated with the current resturant partner");
        }
        return restaurant;
    }

    @Override
    public List<WalletTransaction> getWalletTransactionsByRestaurantId(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWalletTransactionsByRestaurantId'");
    }

}
