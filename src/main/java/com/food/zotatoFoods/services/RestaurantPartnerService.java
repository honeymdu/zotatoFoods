package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.WalletTransaction;

public interface RestaurantPartnerService {
    RestaurantDto createRestaurant(AddNewRestaurantDto addNewRestaurantDto);

    RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId);

    OrderDto acceptOrderRequest(Long orderRequestId, Integer estimatedPreparationTime);

    OrderRequestsDto cancelOrderRequest(Long orderRequestId);

    MenuItemDto updateMenuItemOfMenu(MenuItemDto menuItemDto, Long menuItemId);

    RestaurantPartner getCurrentRestaurantPartner();

    MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId);

    List<WalletTransaction> getWalletTransactionsByRestaurantId(Long restaurantId);

    // RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto,
    // Long restaurantId);

    // MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId);

    // MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long
    // menuItemId);

    // Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest);

    RestaurantPartner createNewRestaurantPartner(RestaurantPartner restaurantPartner);

    Restaurant ViewMyRestaurantProfile(Long RestaurantId);

    // List<Order> getALlOrders(Long restaurantId);

}
