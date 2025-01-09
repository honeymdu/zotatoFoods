package com.food.zotatoFoods.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.entites.RestaurantPartner;
import com.food.zotatoFoods.exceptions.RuntimeConfilictException;
import com.food.zotatoFoods.services.RestaurantPartnerService;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurant-partner")
@RequiredArgsConstructor
public class RestaurantPartnerController {

    private final RestaurantPartnerService restaurantPartnerService;
    private final ModelMapper modelMapper;
    private final RestaurantService restaurantService;

    @PostMapping("/add-my-restaurant")
    public ResponseEntity<Boolean> AddMyRestaurant(@RequestBody RestaurantDto restaurantDto) {
        Restaurant newRestaurant = modelMapper.map(restaurantDto, Restaurant.class);
        RestaurantPartner restaurantPartner = restaurantPartnerService.getCurrentRestaurantPartner();
        newRestaurant.setIsAvailable(false);
        newRestaurant.setRestaurantPartner(restaurantPartner);
        newRestaurant.setRating(0.0);
        // check Restaurant already exist corresponding to current Restaurant_owner
        Boolean isAlreadyExist = restaurantService.IsRestaurentAlreadyExist(newRestaurant);
        if (isAlreadyExist) {
            throw new RuntimeConfilictException("Restaurant Already Exist with name = " + newRestaurant.getName());
        }
        Boolean isCreated = restaurantService.AddNewRestaurant(newRestaurant);
        return new ResponseEntity<>(isCreated, HttpStatus.CREATED);
    }

    @GetMapping("/check-my-Restaurant-active-status/{RestaurantId}")
    public ResponseEntity<Boolean> checkRestaurantStatus(@PathVariable Long RestaurantId) {
        RestaurantPartner restaurantPartner = restaurantPartnerService.getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByIdAndRestaurantPartner(RestaurantId,
                restaurantPartner);
        return ResponseEntity.ok(restaurant.getIsAvailable());
    }

    @GetMapping("/view-my-restaurant-profile/{RestaurantId}")
    public ResponseEntity<Restaurant> ViewMyRestaurantProfile(@PathVariable Long RestaurantId) {
        RestaurantPartner restaurantPartner = restaurantPartnerService.getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getProfile(RestaurantId);
        // Check if the restaurant is assosiate with current Restaurant partner
        if (!restaurant.getRestaurantPartner().equals(restaurantPartner)) {
            throw new RuntimeException("Restaurant is not assositated with the current resturant partner");
        }

        return ResponseEntity.ok(restaurant);

    }

}
