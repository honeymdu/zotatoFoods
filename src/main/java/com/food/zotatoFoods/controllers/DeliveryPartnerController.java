package com.food.zotatoFoods.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.zotatoFoods.dto.ConsumerOTP;
import com.food.zotatoFoods.dto.RestaurantOTP;
import com.food.zotatoFoods.services.DeliveryPartnerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/delivery-partner")
@RequiredArgsConstructor
@Secured("ROLE_DELIVERY_PARTNER")
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/pick-up-order/{deliveryRequestId}")
    public ResponseEntity<Boolean> pickOrderFromRestaurant(@PathVariable Long deliveryRequestId,
            @RequestBody RestaurantOTP restaurantOTP) {
        deliveryPartnerService.pickupOrderFromRestaurant(deliveryRequestId, restaurantOTP.getRestaurantOTP());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/accept-delivery-request/{deliveryRequestId}")
    public ResponseEntity<Boolean> acceptdeliveryRequest(@PathVariable Long deliveryRequestId) {
        deliveryPartnerService.acceptDeliveryRequest(deliveryRequestId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/cancel-delivery-request/{deliveryRequestId}")
    public ResponseEntity<Boolean> canceldeliveryRequest(@PathVariable Long deliveryRequestId) {
        deliveryPartnerService.cancelDeliveryRequest(deliveryRequestId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/complete-delivery-request/{deliveryRequestId}")
    public ResponseEntity<Boolean> completedeliveryRequest(@PathVariable Long deliveryRequestId,
            @RequestBody ConsumerOTP consumerOTP) {
        deliveryPartnerService.completeOrderDelivery(deliveryRequestId, consumerOTP.getConsumerOTP());
        return ResponseEntity.ok(true);
    }

}
