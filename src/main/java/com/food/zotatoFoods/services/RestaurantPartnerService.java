package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.WalletTransaction;

public interface RestaurantPartnerService {

    RestaurantDto createRestaurant(AddNewRestaurantDto addNewRestaurantDto);

    RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId);

    Order acceptOrderRequest(Long orderRequestId);

    OrderRequestsDto cancelOrderRequest(Long orderRequestId);

    Menu updateMenuItemOfMenu(MenuItemDto menuItemDto);

    RestaurantPartner getCurrentRestaurantPartner();

    MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId);

    List<WalletTransaction> getWalletTransactionsByRestaurantId(Long restaurantId);

    RestaurantPartner createNewRestaurantPartner(RestaurantPartner restaurantPartner);

    Restaurant ViewMyRestaurantProfile(Long RestaurantId);

    List<OrderRequests> getAllOrderRequestsByRestaurantId(Long RestaurantId);

    // RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto,
    // Long restaurantId);

    // MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId);

    // MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long
    // menuItemId);

    // Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest);

    // List<Order> getALlOrders(Long restaurantId);

}
