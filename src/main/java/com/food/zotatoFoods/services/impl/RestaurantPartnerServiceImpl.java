package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.entites.User;
import com.food.zotatoFoods.entites.WalletTransaction;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.RestaurantPartnerRepository;
import com.food.zotatoFoods.services.MenuService;
import com.food.zotatoFoods.services.OrderRequestService;
import com.food.zotatoFoods.services.OrderService;
import com.food.zotatoFoods.services.RestaurantPartnerService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;
    private final OrderRequestService orderRequestService;
    private final OrderService orderService;
    private final WalletTransactionService walletTransactionsService;
    private final MenuService menuService;

    @Override
    public RestaurantDto createRestaurant(@Validated AddNewRestaurantDto addNewRestaurantDto) {
        RestaurantDto restaurant = modelMapper.map(addNewRestaurantDto, RestaurantDto.class);
        Restaurant savedRestaurant = restaurantService.AddNewRestaurant(getCurrentRestaurantPartner(), restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public Order acceptOrderRequest(Long orderRequestId) {
        OrderRequests orderRequests = orderRequestService.getOrderRequestById(orderRequestId);
        // check order status
        if (orderRequests.getOrderRequestStatus().equals(OrderRequestStatus.PENDING)) {
            orderRequests.setOrderRequestStatus(OrderRequestStatus.ACCEPTED);
            OrderRequests saveOrderRequests = orderRequestService.save(orderRequests);
            return orderService.createOrder(saveOrderRequests);
        }

        throw new RuntimeException(
                "Can not accept the order Request as the order Request status ="
                        + orderRequests.getOrderRequestStatus());

    }

    @Override
    public OrderRequestsDto cancelOrderRequest(Long orderRequestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrderRequest'");
    }

    @Override
    public Menu updateMenuItemOfMenu(MenuItemDto menuItemDto) {
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
    public Menu createMenuItemForMenu(MenuItemDto menuItemDto, Long restaurantId) {
        return menuService.addMenuItem(restaurantId, menuItemDto);
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
    public List<WalletTransaction> getAllMyWalletTransactions(Long restaurantPartnerId) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        return walletTransactionsService.getAllWalletTransactionByUser(restaurantPartner.getUser());
    }

    @Override
    public List<OrderRequests> getAllOrderRequestsByRestaurantId(Long RestaurantId) {
        return orderRequestService.getAllOrderRequestByRestaurantId(RestaurantId);
    }

}
