package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.food.zotatoFoods.entites.Cart;
import com.food.zotatoFoods.entites.Order;
import com.food.zotatoFoods.entites.OrderRequests;
import com.food.zotatoFoods.entites.enums.OrderRequestStatus;
import com.food.zotatoFoods.entites.enums.PaymentMethod;
import com.food.zotatoFoods.exceptions.ResourceNotFoundException;
import com.food.zotatoFoods.repositories.OrderRequestsRepository;
import com.food.zotatoFoods.services.CartService;
import com.food.zotatoFoods.services.DeliveryService;
import com.food.zotatoFoods.services.OrderRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestsRepository orderRequestsRepository;
    private final CartService cartService;
    private final DeliveryService deliveryService;
    Double PLATFORM_COMMISSION = 10.5;

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
        Double delivery_price = deliveryService.CalculateDeliveryFees(cart.getRestaurant().getRestaurantLocation(),
                UserLocation);
        OrderRequests orderRequests = OrderRequests.builder()
                .cart(cart)
                .consumer(cart.getConsumer())
                .deliveryFee(delivery_price)
                .platformFee(PLATFORM_COMMISSION)
                .foodAmount(cart.getTotalPrice())
                .orderRequestStatus(OrderRequestStatus.PENDING)
                .restaurant(cart.getRestaurant())
                .paymentMethod(paymentMethod)
                .totalPrice(cart.getTotalPrice() + delivery_price + PLATFORM_COMMISSION).build();

        // Send Notification to Corresponding restaurant

        OrderRequests savedOrderRequests = save(orderRequests);
        cartService.inValidCart(cart);
        return savedOrderRequests;
    }

    @Override
    public List<Order> getAllOrderRequestByRestaurantId(Long restaurantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrderRequestByRestaurantId'");
    }

}
