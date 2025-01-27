package com.food.zotatoFoods.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.AddNewRestaurantDto;
import com.food.zotatoFoods.dto.CreateMenu;
import com.food.zotatoFoods.dto.MenuDto;
import com.food.zotatoFoods.dto.MenuItemDto;
import com.food.zotatoFoods.dto.OrderRequestsDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.services.RestaurantPartnerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurant-partner")
@RequiredArgsConstructor
@Secured("ROLE_RESTAURENT_PARTNER")
public class RestaurantPartnerController {

    private final RestaurantPartnerService restaurantPartnerService;
    private final ModelMapper modelMapper;

    @PostMapping("/add-my-restaurant")
    public ResponseEntity<RestaurantDto> AddMyRestaurant(@RequestBody AddNewRestaurantDto addNewRestaurantDto) {
        return new ResponseEntity<>(restaurantPartnerService.createRestaurant(addNewRestaurantDto), HttpStatus.CREATED);
    }

    @PostMapping("/add-my-restaurant-menu-item/{RestaurantId}")
    public ResponseEntity<MenuDto> AddMenuItemToMenu(@PathVariable Long RestaurantId,
            @RequestBody MenuItemDto menuItemDto) {

        return new ResponseEntity<>(
                modelMapper.map(restaurantPartnerService.addMenuItemToMenu(menuItemDto, RestaurantId),
                        MenuDto.class),
                HttpStatus.CREATED);

    }

    @PostMapping("/create-restaurant-menu/{RestaurantId}")
    public ResponseEntity<MenuDto> CreateMenu(@PathVariable Long RestaurantId,
            @RequestBody CreateMenu createMenu) {
        Restaurant restaurant = restaurantPartnerService.ViewMyRestaurantProfile(RestaurantId);
        createMenu.setRestaurant(restaurant);

        return new ResponseEntity<>(
                modelMapper.map(restaurantPartnerService.CreateMenu(createMenu),
                        MenuDto.class),
                HttpStatus.CREATED);

    }

    @PostMapping("/accept-order-Request/{OrderRequestId}")
    public ResponseEntity<Order> acceptOrderRequest(@PathVariable Long OrderRequestId) {
        Order order = restaurantPartnerService.acceptOrderRequest(OrderRequestId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/view-menu/{RestaurantId}")
    public ResponseEntity<MenuDto> viewMenu(@PathVariable Long RestaurantId) {
        Menu menu = restaurantPartnerService.viewMenuByRestaurantId(RestaurantId);
        return ResponseEntity.ok(modelMapper.map(menu, MenuDto.class));
    }

    @GetMapping("/view-Order-Requests/{RestaurantId}")
    public ResponseEntity<List<OrderRequestsDto>> viewOrderRequests(@PathVariable Long RestaurantId) {
        List<OrderRequestsDto> orderRequestsDtos = restaurantPartnerService
                .viewOrderRequestsByRestaurantId(RestaurantId)
                .stream()
                .map(item -> modelMapper.map(item, OrderRequestsDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderRequestsDtos);
    }

    @GetMapping("/check-my-Restaurant-active-status/{RestaurantId}")
    public ResponseEntity<Boolean> checkRestaurantStatus(@PathVariable Long RestaurantId) {
        Restaurant restaurant = restaurantPartnerService.ViewMyRestaurantProfile(RestaurantId);
        return ResponseEntity.ok(restaurant.getIsAvailable());
    }

    @GetMapping("/view-my-restaurant-profile/{RestaurantId}")
    public ResponseEntity<Restaurant> ViewMyRestaurantProfile(@PathVariable Long RestaurantId) {

        Restaurant restaurant = restaurantPartnerService.ViewMyRestaurantProfile(RestaurantId);
        return ResponseEntity.ok(restaurant);

    }

}
