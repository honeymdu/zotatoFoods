package com.food.zotatoFoods.controllers;

import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.entites.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/onBoardRestaurant")
    public ResponseEntity<RestaurantDto> onBoardNewRestaurant(@RequestBody Restaurant restaurant){

        return null;
    }
}
