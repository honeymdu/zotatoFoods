package com.food.zotatoFoods.services.impl;


import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.CartDto;
import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.dto.PointDto;
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
    private final ModelMapper modelMapper;
    private final Double PLATFORM_COMMISSION = 10.5;

    @Override
    public Double calculateTotalPrice(PreOrderRequestDto preOrderRequestDto) {
        return preOrderRequestDto.getFoodAmount() + preOrderRequestDto.getPlatformFee()
                + preOrderRequestDto.getDeliveryFee();
    }

    @Override
    public void validatePreOrderRequest(Long restaurantId, Long ConsumerId, Cart cart) {
        restaurantService.IsRestaurentActiveOrVarified(restaurantId);
        userService.existsById(ConsumerId);
        cartService.isValidCart(cart);
        // isDeliveryAddressValid(cart.getConsumer().getUser().getAddresses().);

    }

    @Override
    public PreOrderRequestDto createPreOrderRequest(Cart cart, Point UserLocation) {

        Double delivery_price = calculateDeliverPrice(cart, UserLocation);
        validatePreOrderRequest(cart.getRestaurant().getId(), cart.getConsumer().getId(), cart);
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        PointDto userLocation = modelMapper.map(UserLocation, PointDto.class);
        PreOrderRequestDto preOrderRequestDto = PreOrderRequestDto.builder()
                .cart(cartDto)
                .consumerId(cart.getConsumer().getId())
                .foodAmount(cart.getTotalPrice())
                .deliveryFee(delivery_price)
                .platformFee(PLATFORM_COMMISSION)
                .totalPrice(cart.getTotalPrice() + (delivery_price) + (PLATFORM_COMMISSION))
                .restaurantId(cart.getRestaurant().getId())
                .currentLocation(userLocation)
                .build();

        return preOrderRequestDto;

    }

    @Override
    public boolean isDeliveryAddressValid(Point UserLocation) {
        return true;
    }

    @Override
    public Double calculateDeliverPrice(Cart cart, Point UserLocation) {
        DeliveryFareCalculationStrategy deliveryFareCalculationStrategy = deliveryStrategyManager
                .deliveryFareCalculationStrategy();
        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder()
                .DropLocation(UserLocation)
                .PickupLocation(cart.getRestaurant().getRestaurantLocation()).build();
        return deliveryFareCalculationStrategy.calculateDeliveryFees(deliveryFareGetDto);
    }

}
