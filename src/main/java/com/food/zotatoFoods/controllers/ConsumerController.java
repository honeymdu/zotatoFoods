package com.food.zotatoFoods.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.entites.Restaurant;
import com.food.zotatoFoods.services.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final RestaurantService restaurantService;

    @GetMapping("/list/get-available-restaurant")
    public ResponseEntity<Page<Restaurant>> viewAvailableRestaurant(
            @RequestParam(defaultValue = "0") Integer PageOffset,
            @RequestParam(defaultValue = "10", required = false) Integer PageSize) {
        PageRequest pageRequest = PageRequest.of(PageOffset, PageSize,
                Sort.by(Sort.Direction.DESC, "id"));
        Page<Restaurant> restaurants = restaurantService.getAllVarifiedRestaurant(pageRequest);
        return ResponseEntity.ok(restaurants);
    }

    

}
