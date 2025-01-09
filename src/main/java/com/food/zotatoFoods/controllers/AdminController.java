package com.food.zotatoFoods.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.DeliveryPartnerDto;
import com.food.zotatoFoods.dto.RestaurantDto;
import com.food.zotatoFoods.dto.RestaurantPartnerDto;
import com.food.zotatoFoods.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/onBoard-Restaurant-Partner/{UserId}")
    public ResponseEntity<RestaurantPartnerDto> onBoardNewRestaurantPartner(@PathVariable Long UserId,
            @RequestBody RestaurantPartnerDto restaurantPartnerDto) {
        return new ResponseEntity<>(adminService.onBoardNewRestaurantPartner(UserId, restaurantPartnerDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/onBoard-Delivery-Partner/{UserId}")
    public ResponseEntity<DeliveryPartnerDto> onBoardNewDeliveryPartner(@PathVariable Long UserId,
            @RequestBody DeliveryPartnerDto deliveryPartnerDto) {
        return new ResponseEntity<>(adminService.onBoardDeliveryPartner(UserId, deliveryPartnerDto),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-delivery-partner/{UserId}")
    public ResponseEntity<Boolean> removeDeliveryPartner(@PathVariable Long UserId) {
        Boolean gotDeleted = adminService.removeDeliveryPartner(UserId);
        if (gotDeleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-Restaurant-Partner/{RestaurantId}")
    public ResponseEntity<Boolean> removeRestayarant(@PathVariable Long RestaurantId) {
        Boolean gotDeleted = adminService.removeRestaurant(RestaurantId);
        if (gotDeleted)
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list/get-all-restaurant")
    public ResponseEntity<Page<RestaurantDto>> getAllRestaurant(@RequestParam(defaultValue = "0") Integer PageOffset,
            @RequestParam(defaultValue = "10", required = false) Integer PageSize) {
        PageRequest pageRequest = PageRequest.of(PageOffset, PageSize,
                Sort.by(Sort.Direction.DESC, "createdTime", "id"));
        return ResponseEntity.ok(adminService.getAllRestaurant(pageRequest));
    }

    @GetMapping("/list/get-all-Delivery-Partner")
    public ResponseEntity<Page<DeliveryPartnerDto>> getAllDeliveryPartner(
            @RequestParam(defaultValue = "0") Integer PageOffset,
            @RequestParam(defaultValue = "10", required = false) Integer PageSize) {
        PageRequest pageRequest = PageRequest.of(PageOffset, PageSize,
                Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(adminService.getAllDeliveryPartner(pageRequest));
    }

    @PostMapping("/varify-Restaurant/{RestaurantId}")
    public ResponseEntity<Boolean> VarifyRestaurant(@PathVariable Long RestaurantId) {
        Boolean IsVarified = adminService.varifyRestaurant(RestaurantId);
        return ResponseEntity.ok(IsVarified);

    }

}
