package com.food.zotatoFoods.services.impl;

import java.math.BigDecimal;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.dto.PreOrderRequestDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.PreOrderRequestService;
import com.food.zotatoFoods.services.RestaurantService;
import com.food.zotatoFoods.strategies.DeliveryFareCalculationStrategy;
import com.food.zotatoFoods.strategies.DeliveryStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreOrderRequestServiceImpl implements PreOrderRequestService {

    private final RestaurantService restaurantService;
    private final UserService userService;
    private final CartService cartService;
    private final DeliveryStrategyManager deliveryStrategyManager;

    @Override
    public BigDecimal calculateTotalPrice(PreOrderRequestDto preOrderRequestDto) {
        return preOrderRequestDto.getFoodAmount()
                .add(preOrderRequestDto.getPlatformFee())
                .add(preOrderRequestDto.getDeliveryFee());
    }

    @Override
    public void validatePreOrderRequest(PreOrderRequestDto preOrderRequestDto) {
        restaurantService.IsRestaurentActiveOrVarified(preOrderRequestDto.getRestaurantId());
        userService.existsById(preOrderRequestDto.getConsumerId());
        cartService.isValidCart(preOrderRequestDto.getCart());
        isDeliveryAddressValid(preOrderRequestDto);

    }

    @Override
    public PreOrderRequestDto createPreOrderRequest(Cart cart, Point UserLocation) {

        BigDecimal delivery_price = calculateDeliverPrice(cart, UserLocation);
        PreOrderRequestDto preOrderRequestDto = PreOrderRequestDto.builder()
                .cart(cart)
                .consumerId(cart.getConsumer().getId())
                .foodAmount(cart.getTotalPrice())
                .deliveryFee(delivery_price)
                .restaurantId(cart.getRestaurant().getId())
                .currentLocation(UserLocation)
                .build();
        validatePreOrderRequest(preOrderRequestDto);
        return preOrderRequestDto;

    }

    @Override
    public boolean isDeliveryAddressValid(PreOrderRequestDto preOrderRequestDto) {
        return true;
    }

    @Override
    public BigDecimal calculateDeliverPrice(Cart cart, Point UserLocation) {
        DeliveryFareCalculationStrategy deliveryFareCalculationStrategy = deliveryStrategyManager
                .deliveryFareCalculationStrategy();
        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder()
                .DropLocation(UserLocation)
                .PickupLocation(cart.getRestaurant().getRestaurantLocation()).build();
        return deliveryFareCalculationStrategy.calculateDeliveryFees(deliveryFareGetDto);
    }

}
