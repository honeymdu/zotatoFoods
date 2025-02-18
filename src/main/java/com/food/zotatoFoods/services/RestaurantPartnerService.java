package com.food.zotatoFoods.services;

import java.util.List;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.dto.RestaurantOTP;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.entites.enums.OrderStatus;

public interface RestaurantPartnerService {

    RestaurantDto createRestaurant(AddNewRestaurantDto addNewRestaurantDto);

    Menu CreateMenu(com.food.zotatoFoods.dto.CreateMenu createMenu);

    Order acceptOrderRequest(Long orderRequestId);

    OrderRequestsDto cancelOrderRequest(Long orderRequestId);

    Menu updateMenuItemOfMenu(MenuItemDto menuItemDto);

    RestaurantPartner getCurrentRestaurantPartner();

    Menu addMenuItemToMenu(MenuItemDto menuItemDto, Long restaurantId);

    List<WalletTransaction> getAllMyWalletTransactions(Long restaurantId);

    RestaurantPartner createNewRestaurantPartner(RestaurantPartner restaurantPartner);

    Restaurant ViewMyRestaurantProfile(Long RestaurantId);

    List<OrderRequests> viewOrderRequestsByRestaurantId(Long RestaurantId);

    Menu viewMenuByRestaurantId(Long RestaurantId);

    Order updateOrderStatus(Long OrderId, OrderStatus orderStatus);

    // RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto,
    // Long restaurantId);

    // MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId);

    // MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long
    // menuItemId);

    // Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest);

    // List<Order> getALlOrders(Long restaurantId);

    RestaurantOTP getRestaurantOTPByOrderId(Long OrderId);
}
