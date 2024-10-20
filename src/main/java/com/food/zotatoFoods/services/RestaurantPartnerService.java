package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.RestaurantPartner;

public interface RestaurantPartnerService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);

    RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId);

    OrderDto acceptOrderRequest(Long orderRequestId, Integer estimatedPreparationTime);

    OrderRequestsDto cancelOrderRequest(Long orderRequestId);

    MenuItemDto updateMenuItemOfMenu(MenuItemDto menuItemDto, Long menuItemId);

    RestaurantPartner getCurrentRestaurantPartner();

    MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId);

    // RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto,
    // Long restaurantId);

    // MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId);

    // MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long
    // menuItemId);

    // Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest);

    RestaurantPartner createNewRestaurantPartner(RestaurantPartner restaurantPartner);

}
