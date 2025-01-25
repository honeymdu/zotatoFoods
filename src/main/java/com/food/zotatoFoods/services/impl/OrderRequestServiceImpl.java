package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.dto.DeliveryFareGetDto;
import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.OrderRequestsRepository;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.OrderRequestService;
import com.food.zotatoFoods.strategies.DeliveryFareCalculationStrategy;
import com.food.zotatoFoods.strategies.DeliveryStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestsRepository orderRequestsRepository;
    private final CartService cartService;
    private final DeliveryStrategyManager deliveryStrategyManager;
    private final Double PLATFORM_COMMISSION = 10.5;

    @Override
    public OrderRequests save(OrderRequests orderRequests) {
        return orderRequestsRepository.save(orderRequests);

    }

    @Override
    public OrderRequests getOrderRequestById(Long OrderRequestId) {
        return orderRequestsRepository.findById(OrderRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order Request Not found with OrderRequestId =" + OrderRequestId));
    }

    @Override
    public OrderRequests OrderRequest(Long CartId, PaymentMethod paymentMethod, Point UserLocation) {
        Cart cart = cartService.getCartById(CartId);
        cartService.isValidCart(cart);
        DeliveryFareCalculationStrategy deliveryFareCalculationStrategy = deliveryStrategyManager
                .deliveryFareCalculationStrategy();
        DeliveryFareGetDto deliveryFareGetDto = DeliveryFareGetDto.builder().DropLocation(UserLocation)
                .PickupLocation(cart.getRestaurant().getRestaurantLocation()).build();
        Double delivery_price = deliveryFareCalculationStrategy.calculateDeliveryFees(deliveryFareGetDto);
        OrderRequests orderRequests = OrderRequests.builder()
                .cart(cart)
                .consumer(cart.getConsumer())
                .deliveryFee(delivery_price)
                .platformFee(PLATFORM_COMMISSION)
                .foodAmount(cart.getTotalPrice())
                .orderRequestStatus(OrderRequestStatus.PENDING)
                .restaurant(cart.getRestaurant())
                .paymentMethod(paymentMethod)
                .totalPrice(cart.getTotalPrice() + (delivery_price + (PLATFORM_COMMISSION))).build();

        // Send Notification to Corresponding restaurant

        OrderRequests savedOrderRequests = save(orderRequests);
        cartService.inValidCart(cart);
        return savedOrderRequests;
    }

    @Override
    public List<OrderRequests> getAllOrderRequestByRestaurantId(Long restaurantId) {
        return orderRequestsRepository.findByRestaurantId(restaurantId);
    }

}
