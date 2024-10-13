package com.food.zotatoFoods.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/onBoardRestaurant/{UserId}")
    public ResponseEntity<RestaurantPartnerDto> onBoardNewRestaurantPartner(@PathVariable Long UserId,
            @RequestBody RestaurantPartnerDto restaurantPartnerDto) {
        return new ResponseEntity<>(adminService.onBoardNewRestaurantPartner(UserId, restaurantPartnerDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/onBoardDeliveryPartner/{UserId}")
    public ResponseEntity<DeliveryPartnerDto> onBoardNewDeliveryPartner(@PathVariable Long UserId,
            @RequestBody DeliveryPartnerDto deliveryPartnerDto) {
        return new ResponseEntity<>(adminService.onBoardDeliveryPartner(UserId, deliveryPartnerDto),
                HttpStatus.CREATED);
    }

}
