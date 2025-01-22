package com.food.zotatoFoods.controllers;

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
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Menu;
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

    @PostMapping("/view-menu/{RestaurantId}")
    public ResponseEntity<MenuDto> viewMenu(@PathVariable Long RestaurantId) {
        Menu menu = restaurantPartnerService.viewMenuByRestaurantId(RestaurantId);
        return ResponseEntity.ok(modelMapper.map(menu, MenuDto.class));
    }

}
